package com.edgecron;

import java.util.*;

public final class EndpointsService {
    private final Transport transport;

    EndpointsService(Transport transport) {
        this.transport = transport;
    }

    public WebhookEndpoint create(CreateEndpointRequest request) {
        return transport.requestJSON("POST", "/v1/endpoints", null, request, WebhookEndpoint.class);
    }

    public WebhookEndpoint get(long id) {
        return transport.requestJSON("GET", "/v1/endpoints/" + id, null, null, WebhookEndpoint.class);
    }

    public WebhookEndpoint update(long id, UpdateEndpointRequest request) {
        return transport.requestJSON("PATCH", "/v1/endpoints/" + id, null, request, WebhookEndpoint.class);
    }

    public EndpointList list(int page, int pageSize, String status) {
        Map<String, String> q = new LinkedHashMap<>();
        q.put("page", String.valueOf(page));
        q.put("page_size", String.valueOf(pageSize));
        if (status != null && !status.isEmpty()) {
            q.put("status", status);
        }
        return transport.requestJSON("GET", "/v1/endpoints", q, null, EndpointList.class);
    }

    public void delete(long id) {
        transport.requestJSON("DELETE", "/v1/endpoints/" + id, null, null, null);
    }

    public void enable(long id) {
        transport.requestJSON("POST", "/v1/endpoints/" + id + "/enable", null, null, null);
    }

    public void disable(long id) {
        transport.requestJSON("POST", "/v1/endpoints/" + id + "/disable", null, null, null);
    }
}
