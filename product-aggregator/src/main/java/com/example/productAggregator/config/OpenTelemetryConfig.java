package com.example.productAggregator.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfig {

    @Value("${otel.exporter.otlp.endpoint}")
    private String otelEndpoint;

    public OpenTelemetryConfig(SystemInfoService systemInfoService) {
        this.systemInfoService = systemInfoService;
    }

    @Bean
    public SpanExporter spanExporter() {
        return OtlpGrpcSpanExporter.builder().setEndpoint(otelEndpoint).build();
    }
    @Bean
    public LogRecordExporter logExporter() { // New: Log exporter
        return OtlpGrpcLogRecordExporter.builder()
                .setEndpoint(otelEndpoint)
                .build();
    }

    @Value("${custom.app.name}")
    private String applicationName;

    @Value("${custom.app.version:1.0.0}")
    private String version;

    @Value("${custom.app.env:production}")
    private String environment;
    private final SystemInfoService systemInfoService;

    @Bean
    public OpenTelemetry openTelemetry() {
        // Create the OTLP exporter
        SpanExporter otlpExporter = spanExporter();

        // Create BatchSpanProcessor to handle the trace data
        BatchSpanProcessor spanProcessor = BatchSpanProcessor.builder(otlpExporter).build();

        // Set up the TracerProvider with custom resource attributes
        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
                .setResource(
                        io.opentelemetry.sdk.resources.Resource.create(
                                Attributes.of(
                        io.opentelemetry.api.common.AttributeKey.stringKey("service.name"), applicationName,
                        io.opentelemetry.api.common.AttributeKey.stringKey("service.instance"), systemInfoService.getSystemInfo(),
                        io.opentelemetry.api.common.AttributeKey.stringKey("app.version"), version,
                        io.opentelemetry.api.common.AttributeKey.stringKey("app.env"), environment)))
                .addSpanProcessor(spanProcessor)
                .build();

        return OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                .buildAndRegisterGlobal();
    }

}