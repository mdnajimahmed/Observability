package com.example.productAggregator.config;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfig {

    @Value("${otel.exporter.otlp.endpoint}")
    private String tracesEndpoint;

    @Bean
    public SpanExporter spanExporter() {
        return OtlpGrpcSpanExporter.builder().setEndpoint(tracesEndpoint).build();
    }

}