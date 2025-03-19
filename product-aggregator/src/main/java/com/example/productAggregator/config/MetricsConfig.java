//package com.example.productAggregator.config;
//
//import io.micrometer.core.instrument.Tag;
//import io.micrometer.core.instrument.config.MeterFilter;
//import io.opentelemetry.api.OpenTelemetry;
//import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
//import io.opentelemetry.sdk.OpenTelemetrySdk;
//import io.opentelemetry.sdk.metrics.SdkMeterProvider;
//import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
//import io.opentelemetry.sdk.resources.Resource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//import java.util.Arrays;
//
//import static io.opentelemetry.semconv.ServiceAttributes.SERVICE_NAME;
//
//@Configuration
//public class MetricsConfig {
//
////    @Bean
////    public OpenTelemetry openTelemetry() {
////        // Create OTLP exporter
////        OtlpGrpcMetricExporter metricExporter = OtlpGrpcMetricExporter.builder()
////                .setEndpoint("http://localhost:4317") // OpenTelemetry Collector endpoint
////                .build();
////
////        // Create MeterProvider with PeriodicMetricReader
////        SdkMeterProvider meterProvider = SdkMeterProvider.builder()
////                .setResource(Resource.getDefault().toBuilder()
////                        .put(SERVICE_NAME, "spring-app")
////                        .build())
////                .registerMetricReader(PeriodicMetricReader.builder(metricExporter)
////                        .setInterval(Duration.ofSeconds(30)) // Adjust interval as needed
////                        .build())
////                .build();
////
////        // Initialize OpenTelemetry
////        return OpenTelemetrySdk.builder()
////                .setMeterProvider(meterProvider)
////                .buildAndRegisterGlobal();
////    }
//
////    @Bean
////    public MeterFilter commonTagsMeterFilter() {
////        return MeterFilter.commonTags(Arrays.asList(
////                Tag.of("application", "spring-app")
////        ));
////    }
//}