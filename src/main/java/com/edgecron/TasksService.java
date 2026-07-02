package com.edgecron;

import java.util.*;

public final class TasksService {
    private final Transport transport;

    TasksService(Transport transport) {
        this.transport = transport;
    }

    public Task create(CreateTaskRequest request) {
        return transport.requestJSON("POST", "/v1/tasks", null, request, Task.class);
    }

    public Task get(long id) {
        return transport.requestJSON("GET", "/v1/tasks/" + id, null, null, Task.class);
    }

    public TaskList list(int page, int pageSize, String status, long scheduleId, long eventId) {
        Map<String, String> q = new LinkedHashMap<>();
        q.put("page", String.valueOf(page));
        q.put("page_size", String.valueOf(pageSize));
        if (status != null && !status.isEmpty()) {
            q.put("status", status);
        }
        if (scheduleId > 0) {
            q.put("schedule_id", String.valueOf(scheduleId));
        }
        if (eventId > 0) {
            q.put("event_id", String.valueOf(eventId));
        }
        return transport.requestJSON("GET", "/v1/tasks", q, null, TaskList.class);
    }

    public void cancel(long id) {
        transport.requestJSON("POST", "/v1/tasks/" + id + "/cancel", null, null, null);
    }
}
