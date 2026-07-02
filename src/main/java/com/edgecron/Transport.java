package com.edgecron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

final class Transport {
    static final String VERSION = "1.0.0";
    private static final int MAX_RESPONSE_BYTES = 10 << 20; // 10 MB

    private final String keyId;
    private final String secret;
    private final String baseUrl;
    private final HttpClient httpClient;
    private final Duration timeout;
    private final ObjectMapper mapper;

    Transport(String keyId, String secret, String baseUrl, HttpClient httpClient, Duration timeout) {
        this.keyId = keyId;
        this.secret = secret;
        this.baseUrl = baseUrl.replaceAll("/+$", "");
        this.httpClient = httpClient;
        this.timeout = timeout;
        this.mapper = new ObjectMapper()
                .registerModule(new Jdk8Module())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    <T> T requestJSON(String method, String path, Map<String, String> query, Object body, Class<T> dataType) {
        byte[] bodyBytes;
        try {
            bodyBytes = body == null ? new byte[0] : mapper.writeValueAsBytes(body);
        } catch (Exception e) {
            throw new RuntimeException("edgecron: marshal request", e);
        }
        return send(method, path, query, bodyBytes, "application/json", dataType);
    }

    private <T> T send(String method, String path, Map<String, String> query, byte[] body, String contentType, Class<T> dataType) {
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature = Signer.sign(secret, timestamp, query, body);
        String url = baseUrl + buildURL(path, query);

        HttpRequest.Builder reqBuilder = HttpRequest.newBuilder(URI.create(url))
                .timeout(timeout)
                .header("Content-Type", contentType)
                .header("X-Key-ID", keyId)
                .header("X-Timestamp", timestamp)
                .header("X-Signature", signature)
                .header("User-Agent", "edgecron-java/" + VERSION);

        if (body.length == 0) {
            reqBuilder.method(method, HttpRequest.BodyPublishers.noBody());
        } else {
            reqBuilder.method(method, HttpRequest.BodyPublishers.ofByteArray(body));
        }

        try {
            HttpResponse<byte[]> response = httpClient.send(reqBuilder.build(), HttpResponse.BodyHandlers.ofByteArray());
            if (response.body().length > MAX_RESPONSE_BYTES) {
                throw new RuntimeException("edgecron: response exceeds 10 MB limit");
            }

            int statusCode = response.statusCode();
            if (statusCode < 200 || statusCode >= 300) {
                ApiError apiError = parseApiError(response.body());
                if (apiError != null) throw apiError;
                throw new RuntimeException("edgecron: http status " + statusCode);
            }

            Envelope env = mapper.readValue(response.body(), Envelope.class);
            if (env.code != 0) {
                throw new ApiError(env.code, env.message, env.request_id);
            }
            if (dataType != null && env.data != null && !env.data.isNull()) {
                return mapper.treeToValue(env.data, dataType);
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException("edgecron: http request failed", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("edgecron: request interrupted", e);
        }
    }

    private ApiError parseApiError(byte[] body) {
        try {
            Envelope env = mapper.readValue(body, Envelope.class);
            if (env.code != 0) {
                return new ApiError(env.code, env.message, env.request_id);
            }
        } catch (Exception ignored) {}
        return null;
    }

    private static String buildURL(String path, Map<String, String> query) {
        if (query == null || query.isEmpty()) return path;
        StringBuilder url = new StringBuilder(path).append('?');
        boolean first = true;
        for (Map.Entry<String, String> e : new TreeMap<>(query).entrySet()) {
            if (!first) url.append('&');
            url.append(URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8))
                    .append('=')
                    .append(URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8));
            first = false;
        }
        return url.toString();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static final class Envelope {
        public int code;
        public String message;
        public String request_id;
        public JsonNode data;
    }
}
