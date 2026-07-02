package com.edgecron;

import java.util.*;

public final class RetriesService {
    private final Transport transport;

    RetriesService(Transport transport) {
        this.transport = transport;
    }

    public RetryPolicy createPolicy(CreateRetryPolicyRequest request) {
        return transport.requestJSON("POST", "/v1/retries/policies", null, request, RetryPolicy.class);
    }

    public RetryPolicy getPolicy(long id) {
        return transport.requestJSON("GET", "/v1/retries/policies/" + id, null, null, RetryPolicy.class);
    }

    public RetryPolicy updatePolicy(long id, UpdateRetryPolicyRequest request) {
        return transport.requestJSON("PATCH", "/v1/retries/policies/" + id, null, request, RetryPolicy.class);
    }

    public void deletePolicy(long id) {
        transport.requestJSON("DELETE", "/v1/retries/policies/" + id, null, null, null);
    }

    public RetryPolicyList listPolicies() {
        return transport.requestJSON("GET", "/v1/retries/policies", null, null, RetryPolicyList.class);
    }

    public RetryJobList listJobs(int page, int pageSize, String status, long deliveryId) {
        Map<String, String> q = new LinkedHashMap<>();
        q.put("page", String.valueOf(page));
        q.put("page_size", String.valueOf(pageSize));
        if (status != null && !status.isEmpty()) {
            q.put("status", status);
        }
        if (deliveryId > 0) {
            q.put("delivery_id", String.valueOf(deliveryId));
        }
        return transport.requestJSON("GET", "/v1/retries/jobs", q, null, RetryJobList.class);
    }

    public void cancelJob(long id) {
        transport.requestJSON("POST", "/v1/retries/jobs/" + id + "/cancel", null, null, null);
    }
}
