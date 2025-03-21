package com.example.productAggregator.config;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
public class PreSecurityFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("PreSecurityFilter: Executing before Spring Security");

        try {
            Span currentSpan = Span.fromContext(Context.current());
            String traceId = currentSpan.getSpanContext().getTraceId();
            String spanId = currentSpan.getSpanContext().getSpanId();
            exchange.getResponse().getHeaders().add("X-TraceId", traceId);
            exchange.getResponse().getHeaders().add("X-SpanId", spanId);
        } catch (Exception e) {
            // Log the error if tracing context is not available
            log.warn("Failed to add tracing headers: {}", e.getMessage());
        }

        return chain.filter(exchange);
    }

}