package com.edgecron;

import java.util.*;

public final class SubscriptionService {
    private final Transport transport;

    SubscriptionService(Transport transport) {
        this.transport = transport;
    }

    public SubscriptionQuota quota() {
        return transport.requestJSON("GET", "/v1/subscription/quota", null, null, SubscriptionQuota.class);
    }

    public UsageRecords usage(String period) {
        Map<String, String> q = null;
        if (period != null && !period.isEmpty()) {
            q = new LinkedHashMap<>();
            q.put("period", period);
        }
        return transport.requestJSON("GET", "/v1/subscription/usage", q, null, UsageRecords.class);
    }

    public ResourceLimits resourceLimits() {
        return transport.requestJSON("GET", "/v1/subscription/resource-limits", null, null, ResourceLimits.class);
    }
}
