package com.edgecron;

import java.util.*;

public final class DeliveriesService {
    private final Transport transport;

    DeliveriesService(Transport transport) {
        this.transport = transport;
    }

    public DeliveryList list(int page, int pageSize, String status, long taskId, long endpointId) {
        Map<String, String> q = new LinkedHashMap<>();
        q.put("page", String.valueOf(page));
        q.put("page_size", String.valueOf(pageSize));
        if (status != null && !status.isEmpty()) {
            q.put("status", status);
        }
        if (taskId > 0) {
            q.put("task_id", String.valueOf(taskId));
        }
        if (endpointId > 0) {
            q.put("endpoint_id", String.valueOf(endpointId));
        }
        return transport.requestJSON("GET", "/v1/deliveries", q, null, DeliveryList.class);
    }

    public RetryDeliveryResult retry(long id) {
        return transport.requestJSON("POST", "/v1/deliveries/" + id + "/retry", null, null, RetryDeliveryResult.class);
    }
}
