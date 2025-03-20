package com.example.productAggregator.config;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TraceIdHeaderFilter implements WebFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Get the current active span (if exists)
        Span currentSpan = Span.fromContext(Context.current());

        // If the span is not null, proceed to add traceId and spanId to response headers
        if (currentSpan != Span.getInvalid()) {
            String traceId = currentSpan.getSpanContext().getTraceId();
            String spanId = currentSpan.getSpanContext().getSpanId();

            // Add TraceId and SpanId headers to the response
            exchange.getResponse().getHeaders().add("X-TraceId", traceId);
            exchange.getResponse().getHeaders().add("X-SpanId", spanId);
        }

        // Continue processing the request
        return chain.filter(exchange);
    }
}
