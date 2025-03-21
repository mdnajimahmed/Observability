//package com.example.productAggregator.config;
//
//import io.opentelemetry.api.OpenTelemetry;
//import io.opentelemetry.api.common.Attributes;
//import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
//import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
//import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
//import io.opentelemetry.sdk.OpenTelemetrySdk;
//import io.opentelemetry.sdk.logs.SdkLoggerProvider;
//import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
//import io.opentelemetry.sdk.logs.export.LogRecordExporter;
//import io.opentelemetry.sdk.metrics.SdkMeterProvider;
//import io.opentelemetry.sdk.metrics.export.MetricExporter;
//import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
//import io.opentelemetry.sdk.trace.SdkTracerProvider;
//import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
//import io.opentelemetry.sdk.trace.export.SpanExporter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
////@Configuration
//public class OpenTelemetryConfig {
//
//    @Value("${otel.exporter.otlp.endpoint}")
//    private String tracesEndpoint;
//
//    @Value("${otel.exporter.otlp.endpoint}")
//    private String logsEndpoint;
//
//    @Value("${otel.exporter.otlp.endpoint}")
//    private String metricsEndpoint;
//
//    private final SystemInfoService systemInfoService;
//
//    public OpenTelemetryConfig(SystemInfoService systemInfoService) {
//        this.systemInfoService = systemInfoService;
//    }
//
//    // Tracing Exporter
//    @Bean
//    public SpanExporter spanExporter() {
//        return OtlpGrpcSpanExporter.builder()
//                .setEndpoint(tracesEndpoint)
//                .build();
//    }
//
//    // Logging Exporter
//    @Bean
//    public LogRecordExporter logExporter() {
//        return OtlpGrpcLogRecordExporter.builder()
//                .setEndpoint(logsEndpoint)
//                .build();
//    }
//
//    // Metrics Exporter
//    @Bean
//    public MetricExporter metricExporter() {
//        return OtlpGrpcMetricExporter.builder()
//                .setEndpoint(metricsEndpoint)
//                .build();
//    }
//
//    @Value("${custom.app.name}")
//    private String applicationName;
//
//    @Value("${custom.app.version:1.0.0}")
//    private String version;
//
//    @Value("${custom.app.env:production}")
//    private String environment;
//
//    @Bean
//    public OpenTelemetry openTelemetry() {
//        // Common Resource Attributes
//        io.opentelemetry.sdk.resources.Resource resource = io.opentelemetry.sdk.resources.Resource.create(
//                Attributes.of(
//                        io.opentelemetry.api.common.AttributeKey.stringKey("service.name"), applicationName,
//                        io.opentelemetry.api.common.AttributeKey.stringKey("service.instance"), systemInfoService.getSystemInfo(),
//                        io.opentelemetry.api.common.AttributeKey.stringKey("app.version"), version,
//                        io.opentelemetry.api.common.AttributeKey.stringKey("app.env"), environment));
//
//        // Tracing Setup
//        SpanExporter otlpSpanExporter = spanExporter();
//        BatchSpanProcessor spanProcessor = BatchSpanProcessor.builder(otlpSpanExporter).build();
//        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
//                .setResource(resource)
//                .addSpanProcessor(spanProcessor)
//                .build();
//
//        // Logging Setup
//        LogRecordExporter otlpLogExporter = logExporter();
//        BatchLogRecordProcessor logProcessor = BatchLogRecordProcessor.builder(otlpLogExporter).build();
//        SdkLoggerProvider loggerProvider = SdkLoggerProvider.builder()
//                .setResource(resource)
//                .addLogRecordProcessor(logProcessor)
//                .build();
//
//        // Metrics Setup
//        MetricExporter otlpMetricExporter = metricExporter();
//        PeriodicMetricReader metricReader = PeriodicMetricReader.builder(otlpMetricExporter).build();
//
//        // Configure the SdkMeterProvider to include resource attributes as metric labels
//        SdkMeterProvider meterProvider = SdkMeterProvider.builder()
//                .setResource(resource)
//                .registerMetricReader(metricReader)
//                .build();
//
//        // Build OpenTelemetry SDK with all three providers
//        return OpenTelemetrySdk.builder()
//                .setTracerProvider(tracerProvider)
//                .setLoggerProvider(loggerProvider)
//                .setMeterProvider(meterProvider)
//                .buildAndRegisterGlobal();
//    }
//}