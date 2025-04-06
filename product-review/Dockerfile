FROM gradle:8.6-jdk21 AS builder
WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle
RUN gradle dependencies --no-daemon

COPY src /app/src
RUN gradle bootJar --no-daemon

FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

ENV CHECK_VARS="OTEL_SERVICE_NAME OTEL_RESOURCE_ATTRIBUTES DB_HOST DB_USER DB_PASS DB_NAME"

COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

EXPOSE 6565
ENTRYPOINT ["/app/entrypoint.sh"]