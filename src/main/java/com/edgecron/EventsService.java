package com.edgecron;

import java.util.*;

public final class EventsService {
    private final Transport transport;

    EventsService(Transport transport) {
        this.transport = transport;
    }

    public PublishEventResult publish(PublishEventRequest request) {
        return transport.requestJSON("POST", "/v1/events", null, request, PublishEventResult.class);
    }

    public Event get(long id) {
        return transport.requestJSON("GET", "/v1/events/" + id, null, null, Event.class);
    }

    public EventList list(int page, int pageSize, String eventName, String status) {
        Map<String, String> q = new LinkedHashMap<>();
        q.put("page", String.valueOf(page));
        q.put("page_size", String.valueOf(pageSize));
        if (eventName != null && !eventName.isEmpty()) {
            q.put("event_name", eventName);
        }
        if (status != null && !status.isEmpty()) {
            q.put("status", status);
        }
        return transport.requestJSON("GET", "/v1/events", q, null, EventList.class);
    }

    public void enable(long id) {
        transport.requestJSON("POST", "/v1/events/" + id + "/enable", null, null, null);
    }

    public void disable(long id) {
        transport.requestJSON("POST", "/v1/events/" + id + "/disable", null, null, null);
    }

    public void delete(long id) {
        transport.requestJSON("DELETE", "/v1/events/" + id, null, null, null);
    }
}
