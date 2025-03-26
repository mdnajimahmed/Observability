package com.example.productReview.config;

import io.grpc.*;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OpenTelemetryGrpcServerInterceptor implements ServerInterceptor {
    private static final io.grpc.Context.Key<String> TraceContextKey = io.grpc.Context.key("trace-id");

    private final Tracer tracer;
    private final OpenTelemetry openTelemetry;

    public OpenTelemetryGrpcServerInterceptor(OpenTelemetry otel) {
        // can I give any name?
        this.openTelemetry = otel;
        this.tracer = openTelemetry.getTracer("grpc-server");
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        log.info("OpenTelemetryGrpcServerInterceptor");

        io.opentelemetry.context.Context extractedContext = openTelemetry.getPropagators().getTextMapPropagator()
                .extract(Context.current(), headers, new MetadataTextMapGetter());

        Span serverSpan = tracer.spanBuilder("grpc:" + call.getMethodDescriptor().getFullMethodName())
                .setParent(extractedContext)
                .startSpan();

//        io.grpc.Context grpcContext =  io.grpc.Context.current().withValue(
//                TraceContextKey, serverSpan.getSpanContext().getTraceId());

        io.grpc.Context grpcContext = io.grpc.Context.current()
                .withValue(TraceContextKey, serverSpan.getSpanContext().getTraceId())
                .withValue(io.grpc.Context.key("otel-context"), extractedContext);

        try (Scope scope = serverSpan.makeCurrent()) {
            // Now you can access the proper trace and span IDs
            String traceId = serverSpan.getSpanContext().getTraceId();
            String spanId = serverSpan.getSpanContext().getSpanId();
            log.info("OpenTelemetryGrpcServerInterceptor traceId: {}, spanId: {}", traceId, spanId);

            // Continue processing the call
            return Contexts.interceptCall(grpcContext, call, headers, next);
        } finally {
            serverSpan.end();
        }
    }
}
