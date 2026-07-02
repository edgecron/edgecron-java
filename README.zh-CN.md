# EdgeCron Java SDK

EdgeCron Java SDK 是 EdgeCron Webhook 调度与回调投递平台的官方 Java 客户端。

调度延迟 HTTP 请求，可靠投递 Webhook，自动重试失败调用 — 完整执行历史，确保不遗漏。

English README: [README.md](README.md)

## 安装

Maven：

```xml
<dependency>
  <groupId>com.edgecron</groupId>
  <artifactId>edgecron-java</artifactId>
  <version>1.0.0</version>
</dependency>
```

Gradle：

```groovy
implementation 'com.edgecron:edgecron-java:1.0.0'
```

要求：

- Java 17+

## 快速开始

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

## 模块说明

| 客户端方法                   | 说明                         |
|-----------------------------|-----------------------------|
| `client.schedules.*`        | Cron 调度器 CRUD、暂停、恢复   |
| `client.tasks.*`            | 任务执行实例、取消             |
| `client.events.*`           | 事件发布与管理                |
| `client.endpoints.*`        | Webhook 端点配置              |
| `client.deliveries.*`       | 投递记录与手动重试             |
| `client.retries.*`          | 重试策略与任务                |
| `client.subscription.*`     | 配额、用量与资源限制           |

## 配置项

- `baseURL(...)`：自定义 API 地址
- `timeout(Duration)`：超时时间
- `httpClient(HttpClient)`：自定义 HTTP 客户端

## 错误处理

服务端业务错误会抛出 `ApiError`。

```java
import com.edgecron.ApiError;

try {
    client.schedules.get(123);
} catch (ApiError e) {
    System.out.println(e.getCode() + " " + e.getMessage() + " " + e.getRequestId());
}
```

## 安全说明

这是服务端 SDK，不要在浏览器、移动端或不可信客户端暴露 `secret`。
