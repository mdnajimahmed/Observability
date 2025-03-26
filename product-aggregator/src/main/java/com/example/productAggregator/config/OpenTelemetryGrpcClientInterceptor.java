//package com.example.productAggregator.config;
//
//import io.grpc.*;
//import io.opentelemetry.api.OpenTelemetry;
//import io.opentelemetry.api.trace.Span;
//import io.opentelemetry.api.trace.SpanKind;
//import io.opentelemetry.api.trace.Tracer;
//import io.opentelemetry.context.Scope;
//import io.opentelemetry.context.propagation.TextMapPropagator;
//import io.opentelemetry.context.propagation.TextMapSetter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//@Component
//@Slf4j
//public class OpenTelemetryGrpcClientInterceptor implements ClientInterceptor {
//
//    private final Tracer tracer;
//    private final TextMapPropagator propagator;
//
//    public OpenTelemetryGrpcClientInterceptor(OpenTelemetry openTelemetry) {
//        this.tracer = openTelemetry.getTracer("grpc-client");
//        this.propagator = openTelemetry.getPropagators().getTextMapPropagator();
//    }
//
//    @Override
//    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
//            MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
//
//        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
//            @Override
//            public void start(Listener<RespT> responseListener, Metadata headers) {
//                log.info("Starting grpc client call");
//                // Create a new TextMapSetter for the Metadata
//                TextMapSetter<Metadata> setter = new TextMapSetter<Metadata>() {
//                    @Override
//                    public void set(Metadata carrier, String key, String value) {
//                        carrier.put(Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER), value);
//                    }
//                };
//
//                // Inject the OpenTelemetry context into the headers
//                propagator.inject(io.opentelemetry.context.Context.current(), headers, setter);
//
//                Span span = tracer.spanBuilder(method.getFullMethodName())
//                        .setSpanKind(SpanKind.CLIENT)
//                        .startSpan();
//                try (Scope scope = span.makeCurrent()) {
//                    super.start(responseListener, headers);
//                } finally {
//                    span.end();
//                }
//            }
//        };
//    }
//}
//
//
