package com.example.productReview.config;

import io.grpc.ServerInterceptor;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.grpc.server.GlobalServerInterceptor;

@Configuration
public class OpenTelemetryConfig {

//    @Bean
//    public OpenTelemetry openTelemetry() {
//        return OpenTelemetrySdk.builder()
//                .setTracerProvider(SdkTracerProvider.builder()
//                        .addSpanProcessor(BatchSpanProcessor.builder(OtlpGrpcSpanExporter.builder()
//                                .setEndpoint("http://localhost:4317")
//                                .build()).build())
//                        .build())
//                .setPropagators(ContextPropagators.create(
//                        TextMapPropagator.composite(W3CTraceContextPropagator.getInstance())))
//                .build();
//    }
//
//    @Bean
//    public Tracer tracer(OpenTelemetry openTelemetry) {
//        return openTelemetry.getTracer("grpc-tracing");
//    }

    @Bean
    @Order(100)
    @GlobalServerInterceptor
    ServerInterceptor myGlobalLoggingInterceptor(OpenTelemetry openTelemetry) {
        return new OpenTelemetryGrpcServerInterceptor(openTelemetry);
    }

}