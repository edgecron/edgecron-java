package com.edgecron;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.regex.Pattern;

public final class EdgeCronClient {
    private static final Pattern KEY_ID_PATTERN = Pattern.compile("^ak_[0-9a-zA-Z_]+$");
    public static final String VERSION = "1.0.0";

    private final Transport transport;

    public final SchedulesService schedules;
    public final TasksService tasks;
    public final EventsService events;
    public final EndpointsService endpoints;
    public final DeliveriesService deliveries;
    public final RetriesService retries;
    public final SubscriptionService subscription;

    private EdgeCronClient(Builder builder) {
        if (!KEY_ID_PATTERN.matcher(builder.keyId).matches()) {
            throw new IllegalArgumentException("edgecron: keyID must match ak_<hex>, got: " + builder.keyId);
        }
        if (builder.secret == null || builder.secret.isEmpty()) {
            throw new IllegalArgumentException("edgecron: secret must not be empty");
        }

        HttpClient httpClient = builder.httpClient;
        if (httpClient == null) {
            httpClient = HttpClient.newBuilder()
                    .connectTimeout(builder.timeout)
                    .build();
        }

        transport = new Transport(builder.keyId, builder.secret, builder.baseURL, httpClient, builder.timeout);

        schedules = new SchedulesService(transport);
        tasks = new TasksService(transport);
        events = new EventsService(transport);
        endpoints = new EndpointsService(transport);
        deliveries = new DeliveriesService(transport);
        retries = new RetriesService(transport);
        subscription = new SubscriptionService(transport);
    }

    public static EdgeCronClient create(String keyId, String secret) {
        return builder().keyId(keyId).secret(secret).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String keyId;
        private String secret;
        private String baseURL = "https://api.edgecron.com";
        private HttpClient httpClient;
        private Duration timeout = Duration.ofSeconds(30);

        Builder() {}

        public Builder keyId(String keyId) { this.keyId = keyId; return this; }
        public Builder secret(String secret) { this.secret = secret; return this; }
        public Builder baseURL(String baseURL) { this.baseURL = baseURL; return this; }
        public Builder httpClient(HttpClient httpClient) { this.httpClient = httpClient; return this; }
        public Builder timeout(Duration timeout) { this.timeout = timeout; return this; }
        public EdgeCronClient build() { return new EdgeCronClient(this); }
    }
}
