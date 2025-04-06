//package com.example.productAggregator.config;
//
//import io.micrometer.core.instrument.Clock;
//import io.micrometer.core.instrument.MeterRegistry;
//import io.opentelemetry.api.OpenTelemetry;
//import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
//import io.opentelemetry.sdk.OpenTelemetrySdk;
//import io.opentelemetry.sdk.metrics.SdkMeterProvider;
//import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//
//@Configuration
//public class MetricsConfig {
//    @Bean
//    public MeterRegistry otlpGrpcMeterRegistry(OpenTelemetry openTelemetry) {
//        // Set up OTLP gRPC exporter
//        OtlpGrpcMetricExporter exporter = OtlpGrpcMetricExporter.builder()
//                .setEndpoint("http://otel-collector:4317")
//                .build();
//
//        // Register the exporter with a periodic reader
//        SdkMeterProvider meterProvider = SdkMeterProvider.builder()
//                .registerMetricReader(PeriodicMetricReader.builder(exporter)
//                        .setInterval(Duration.ofSeconds(10))
//                        .build())
//                .build();
//
//        OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
//                .setMeterProvider(meterProvider)
//                .build();
//
//        // Hook into Micrometer via OpenTelemetryMeterRegistry
//        return new OpenTelemetryMeterRegistry(
//                OpenTelemetryConfig.DEFAULT,
//                openTelemetry.getMeterProvider(),
//                Clock.SYSTEM
//        );
//    }
//}
