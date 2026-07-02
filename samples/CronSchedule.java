import com.edgecron.EdgeCronClient;
import com.edgecron.Models.*;

import java.util.List;

public class CronSchedule {
    public static void main(String[] args) {
        String keyId = System.getenv("EDGECRON_KEY_ID");
        String secret = System.getenv("EDGECRON_SECRET");

        if (keyId == null || secret == null) {
            System.err.println("Set EDGECRON_KEY_ID and EDGECRON_SECRET environment variables.");
            System.exit(1);
        }

        EdgeCronClient client = EdgeCronClient.builder()
            .keyId(keyId)
            .secret(secret)
            .baseURL("http://localhost:8888")
            .build();

        try {
            Schedule schedule = client.schedules.create(
                new CreateScheduleRequest("every-5-minutes", "*/5 * * * *")
            );
            System.out.println("Created schedule: " + schedule.id + " (" + schedule.name + ")");

            Schedule fetched = client.schedules.get(schedule.id);
            System.out.println("Fetched schedule: " + fetched.name + " cron=" + fetched.cronExpr);

            client.schedules.pause(schedule.id);
            System.out.println("Paused schedule: " + schedule.id);

            client.schedules.resume(schedule.id);
            System.out.println("Resumed schedule: " + schedule.id);

            ScheduleList list = client.schedules.list(1, 10, "active");
            System.out.println("Total schedules: " + list.total);

            UpdateScheduleRequest update = new UpdateScheduleRequest();
            update.name = "every-5-minutes-updated";
            Schedule updated = client.schedules.update(schedule.id, update);
            System.out.println("Updated schedule: " + updated.name);

            client.schedules.delete(schedule.id);
            System.out.println("Deleted schedule: " + schedule.id);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
