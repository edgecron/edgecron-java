package com.edgecron;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

// --- Schedules ---

final class Schedule {
    @JsonProperty("id") public long id;
    @JsonProperty("app_id") public String appId;
    @JsonProperty("name") public String name;
    @JsonProperty("cron_expr") public String cronExpr;
    @JsonProperty("timezone") public String timezone;
    @JsonProperty("payload") public String payload;
    @JsonProperty("status") public String status;
    @JsonProperty("next_run_at") public long nextRunAt;
    @JsonProperty("endpoint_ids") public List<Long> endpointIds;
    @JsonProperty("endpoint_names") public Map<Long, String> endpointNames;
    @JsonProperty("created_at") public long createdAt;
    @JsonProperty("updated_at") public long updatedAt;
}

final class CreateScheduleRequest {
    @JsonProperty("name") public String name;
    @JsonProperty("cron_expr") public String cronExpr;
    @JsonProperty("timezone") public String timezone;
    @JsonProperty("payload") public String payload;
    @JsonProperty("endpoint_ids") public List<Long> endpointIds;

    public CreateScheduleRequest() {}
    public CreateScheduleRequest(String name, String cronExpr) {
        this.name = name;
        this.cronExpr = cronExpr;
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
final class UpdateScheduleRequest {
    @JsonProperty("name") public String name;
    @JsonProperty("cron_expr") public String cronExpr;
    @JsonProperty("timezone") public String timezone;
    @JsonProperty("payload") public String payload;
    @JsonProperty("endpoint_ids") public List<Long> endpointIds;
}

final class ScheduleList {
    @JsonProperty("total") public long total;
    @JsonProperty("list") public List<Schedule> list;
}

// --- Tasks ---

final class Task {
    @JsonProperty("id") public long id;
    @JsonProperty("app_id") public String appId;
    @JsonProperty("schedule_id") public long scheduleId;
    @JsonProperty("event_id") public long eventId;
    @JsonProperty("endpoint_id") public long endpointId;
    @JsonProperty("task_type") public String taskType;
    @JsonProperty("payload") public String payload;
    @JsonProperty("status") public String status;
    @JsonProperty("run_at") public long runAt;
    @JsonProperty("created_at") public long createdAt;
    @JsonProperty("updated_at") public long updatedAt;
}

final class CreateTaskRequest {
    @JsonProperty("endpoint_id") public long endpointId;
    @JsonProperty("payload") public String payload;
    @JsonProperty("run_at") public long runAt;

    public CreateTaskRequest() {}
    public CreateTaskRequest(long endpointId) { this.endpointId = endpointId; }
}

final class TaskList {
    @JsonProperty("total") public long total;
    @JsonProperty("list") public List<Task> list;
}

// --- Events ---

final class Event {
    @JsonProperty("id") public long id;
    @JsonProperty("app_id") public String appId;
    @JsonProperty("event_name") public String eventName;
    @JsonProperty("event_key") public String eventKey;
    @JsonProperty("payload") public String payload;
    @JsonProperty("status") public String status;
    @JsonProperty("created_at") public long createdAt;
}

final class PublishEventRequest {
    @JsonProperty("event_name") public String eventName;
    @JsonProperty("event_key") public String eventKey;
    @JsonProperty("payload") public String payload;

    public PublishEventRequest() {}
    public PublishEventRequest(String eventName, String eventKey) {
        this.eventName = eventName;
        this.eventKey = eventKey;
    }
}

final class PublishEventResult {
    @JsonProperty("id") public long id;
    @JsonProperty("app_id") public String appId;
    @JsonProperty("event_name") public String eventName;
    @JsonProperty("event_key") public String eventKey;
    @JsonProperty("payload") public String payload;
    @JsonProperty("status") public String status;
    @JsonProperty("fanout_count") public int fanoutCount;
    @JsonProperty("created_at") public long createdAt;
}

final class EventList {
    @JsonProperty("total") public long total;
    @JsonProperty("list") public List<Event> list;
}

// --- Webhook Endpoints ---

final class WebhookEndpoint {
    @JsonProperty("id") public long id;
    @JsonProperty("app_id") public String appId;
    @JsonProperty("name") public String name;
    @JsonProperty("url") public String url;
    @JsonProperty("method") public String method;
    @JsonProperty("headers") public String headers;
    @JsonProperty("secret") public String secret;
    @JsonProperty("timeout_ms") public int timeoutMs;
    @JsonProperty("retry_policy_id") public long retryPolicyId;
    @JsonProperty("filter_events") public String filterEvents;
    @JsonProperty("status") public String status;
    @JsonProperty("created_at") public long createdAt;
    @JsonProperty("updated_at") public long updatedAt;
}

final class CreateEndpointRequest {
    @JsonProperty("name") public String name;
    @JsonProperty("url") public String url;
    @JsonProperty("method") public String method;
    @JsonProperty("headers") public String headers;
    @JsonProperty("secret") public String secret;
    @JsonProperty("timeout_ms") public int timeoutMs;
    @JsonProperty("retry_policy_id") public long retryPolicyId;
    @JsonProperty("filter_events") public String filterEvents;

    public CreateEndpointRequest() {}
    public CreateEndpointRequest(String name, String url) { this.name = name; this.url = url; }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
final class UpdateEndpointRequest {
    @JsonProperty("name") public String name;
    @JsonProperty("url") public String url;
    @JsonProperty("method") public String method;
    @JsonProperty("headers") public String headers;
    @JsonProperty("secret") public String secret;
    @JsonProperty("timeout_ms") public Integer timeoutMs;
    @JsonProperty("retry_policy_id") public Long retryPolicyId;
    @JsonProperty("filter_events") public String filterEvents;
}

final class EndpointList {
    @JsonProperty("total") public long total;
    @JsonProperty("list") public List<WebhookEndpoint> list;
}

// --- Deliveries ---

final class Delivery {
    @JsonProperty("id") public long id;
    @JsonProperty("app_id") public String appId;
    @JsonProperty("task_id") public long taskId;
    @JsonProperty("endpoint_id") public long endpointId;
    @JsonProperty("attempt") public int attempt;
    @JsonProperty("status") public String status;
    @JsonProperty("http_status") public int httpStatus;
    @JsonProperty("latency_ms") public int latencyMs;
    @JsonProperty("request_body_hash") public String requestBodyHash;
    @JsonProperty("error_message") public String errorMessage;
    @JsonProperty("next_retry_at") public long nextRetryAt;
    @JsonProperty("created_at") public long createdAt;
    @JsonProperty("updated_at") public long updatedAt;
}

final class DeliveryList {
    @JsonProperty("total") public long total;
    @JsonProperty("list") public List<Delivery> list;
}

final class RetryDeliveryResult {
    @JsonProperty("delivery_id") public long deliveryId;
    @JsonProperty("retry_job_id") public long retryJobId;
    @JsonProperty("status") public String status;
}

// --- Retry Policies ---

final class RetryPolicy {
    @JsonProperty("id") public long id;
    @JsonProperty("app_id") public String appId;
    @JsonProperty("name") public String name;
    @JsonProperty("max_attempts") public int maxAttempts;
    @JsonProperty("backoff_type") public String backoffType;
    @JsonProperty("initial_delay_sec") public int initialDelaySec;
    @JsonProperty("max_delay_sec") public int maxDelaySec;
    @JsonProperty("status") public String status;
    @JsonProperty("created_at") public long createdAt;
    @JsonProperty("updated_at") public long updatedAt;
}

final class CreateRetryPolicyRequest {
    @JsonProperty("name") public String name;
    @JsonProperty("max_attempts") public int maxAttempts;
    @JsonProperty("backoff_type") public String backoffType;
    @JsonProperty("initial_delay_sec") public int initialDelaySec;
    @JsonProperty("max_delay_sec") public int maxDelaySec;

    public CreateRetryPolicyRequest() {}
    public CreateRetryPolicyRequest(String name) { this.name = name; }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
final class UpdateRetryPolicyRequest {
    @JsonProperty("name") public String name;
    @JsonProperty("max_attempts") public Integer maxAttempts;
    @JsonProperty("backoff_type") public String backoffType;
    @JsonProperty("initial_delay_sec") public Integer initialDelaySec;
    @JsonProperty("max_delay_sec") public Integer maxDelaySec;
    @JsonProperty("status") public String status;
}

final class RetryPolicyList {
    @JsonProperty("total") public long total;
    @JsonProperty("list") public List<RetryPolicy> list;
}

// --- Retry Jobs ---

final class RetryJob {
    @JsonProperty("id") public long id;
    @JsonProperty("app_id") public String appId;
    @JsonProperty("delivery_id") public long deliveryId;
    @JsonProperty("attempt") public int attempt;
    @JsonProperty("status") public String status;
    @JsonProperty("run_at") public long runAt;
    @JsonProperty("locked_until") public long lockedUntil;
    @JsonProperty("last_error") public String lastError;
    @JsonProperty("created_at") public long createdAt;
    @JsonProperty("updated_at") public long updatedAt;
}

final class RetryJobList {
    @JsonProperty("total") public long total;
    @JsonProperty("list") public List<RetryJob> list;
}

// --- Subscription ---

final class SubscriptionQuota {
    @JsonProperty("plan_code") public String planCode;
    @JsonProperty("billing_cycle") public String billingCycle;
    @JsonProperty("quota") public long quota;
    @JsonProperty("used") public long used;
    @JsonProperty("remaining") public long remaining;
    @JsonProperty("exceeded") public Boolean exceeded;
    @JsonProperty("current_period_start") public long currentPeriodStart;
    @JsonProperty("current_period_end") public long currentPeriodEnd;
    @JsonProperty("usage_percent") public Double usagePercent;
}

final class UsageRecordItem {
    @JsonProperty("event_type") public String eventType;
    @JsonProperty("period") public String period;
    @JsonProperty("count") public long count;
}

final class UsageRecords {
    @JsonProperty("period") public String period;
    @JsonProperty("total_events") public long totalEvents;
    @JsonProperty("items") public List<UsageRecordItem> items;
}

final class ResourceLimits {
    @JsonProperty("max_cron_jobs") public int maxCronJobs;
    @JsonProperty("current_cron_jobs") public int currentCronJobs;
    @JsonProperty("max_endpoints") public int maxEndpoints;
    @JsonProperty("current_endpoints") public int currentEndpoints;
    @JsonProperty("log_retention_days") public int logRetentionDays;
}
