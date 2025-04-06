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

# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "app.jar"]

# Fail fast if any required environment variables are missing
ENV CHECK_VARS="OTEL_SERVICE_NAME OTEL_RESOURCE_ATTRIBUTES JWT_ISSUER_URI"

# Copy a simple entrypoint script
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

EXPOSE 8080
ENTRYPOINT ["/app/entrypoint.sh"]