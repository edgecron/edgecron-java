# EdgeCron Java SDK

Official Java SDK for the EdgeCron webhook scheduling and callback delivery platform.

Schedule delayed HTTP requests, deliver webhooks reliably, and automatically retry failed calls — with full execution history so nothing gets lost.

中文文档：[README.zh-CN.md](README.zh-CN.md)

## Install

```xml
<dependency>
  <groupId>com.edgecron</groupId>
  <artifactId>edgecron-java</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Quick Start

```java
import com.edgecron.EdgeCronClient;
import com.edgecron.Models.*;

EdgeCronClient client = EdgeCronClient.builder()
    .keyId("ak_xxx")
    .secret("sk_xxx")
    .build();

try {
    Schedule schedule = client.schedules.create(
        new CreateScheduleRequest("my-schedule", "*/5 * * * *")
    );
    System.out.println(schedule.id());
} catch (ApiError e) {
    System.out.println(e.getCode() + " " + e.getMessage() + " " + e.getRequestId());
}
```

## Modules

| Client method                 | Description                        |
|-------------------------------|------------------------------------|
| `client.schedules.*`          | Cron schedule CRUD, pause, resume  |
| `client.tasks.*`              | Task execution instances, cancel   |
| `client.events.*`             | Event publishing and management    |
| `client.endpoints.*`          | Webhook endpoint configuration     |
| `client.deliveries.*`         | Delivery attempt records and retry |
| `client.retries.*`            | Retry policies and jobs            |
| `client.subscription.*`       | Quota, usage, and resource limits  |

## Configuration

- `baseURL(...)` — override API base URL
- `timeout(Duration)` — HTTP client timeout
- `httpClient(HttpClient)` — custom `java.net.http.HttpClient`

## Error Handling

Service-side business errors throw `ApiError`.

```java
import com.edgecron.ApiError;

try {
    client.schedules.get(123);
} catch (ApiError e) {
    System.out.println(e.getCode() + " " + e.getMessage() + " " + e.getRequestId());
}
```

## Security Notice

This is a server-side SDK. Do not expose `secret` in browsers or mobile apps.
