package com.edgecron;

import java.util.*;

public final class SchedulesService {
    private final Transport transport;

    SchedulesService(Transport transport) {
        this.transport = transport;
    }

    public Schedule create(CreateScheduleRequest request) {
        return transport.requestJSON("POST", "/v1/schedules", null, request, Schedule.class);
    }

    public Schedule get(long id) {
        return transport.requestJSON("GET", "/v1/schedules/" + id, null, null, Schedule.class);
    }

    public Schedule update(long id, UpdateScheduleRequest request) {
        return transport.requestJSON("PATCH", "/v1/schedules/" + id, null, request, Schedule.class);
    }

    public ScheduleList list(int page, int pageSize, String status) {
        Map<String, String> q = new LinkedHashMap<>();
        q.put("page", String.valueOf(page));
        q.put("page_size", String.valueOf(pageSize));
        if (status != null && !status.isEmpty()) {
            q.put("status", status);
        }
        return transport.requestJSON("GET", "/v1/schedules", q, null, ScheduleList.class);
    }

    public void delete(long id) {
        transport.requestJSON("DELETE", "/v1/schedules/" + id, null, null, null);
    }

    public void pause(long id) {
        transport.requestJSON("POST", "/v1/schedules/" + id + "/pause", null, null, null);
    }

    public void resume(long id) {
        transport.requestJSON("POST", "/v1/schedules/" + id + "/resume", null, null, null);
    }
}
