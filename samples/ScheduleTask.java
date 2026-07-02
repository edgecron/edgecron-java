import com.edgecron.EdgeCronClient;
import com.edgecron.Models.*;

public class ScheduleTask {
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
            WebhookEndpoint endpoint = client.endpoints.create(
                new CreateEndpointRequest("my-webhook", "https://httpbin.org/post")
            );
            System.out.println("Endpoint: " + endpoint.id());

            Task task = client.tasks.create(
                new CreateTaskRequest(endpoint.id())
            );
            System.out.println("Task: " + task.id());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
