package com.example.productAggregator.config;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
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
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .switchIfEmpty(Mono.defer(() -> letGo(exchange, chain))) // Handle empty SecurityContext
                .filter(authentication -> authentication != null && authentication.getPrincipal() instanceof Jwt)
                .flatMap(authentication -> {
                    try {
                        Jwt jwt = (Jwt) authentication.getPrincipal();
                        String email = jwt.getClaimAsString("email"); // Extract email from JWT

                        // Add email to the current span (if tracing is enabled)
                        Span currentSpan = Span.fromContext(Context.current());
                        currentSpan.setAttribute("user.email", email);

                        // Add trace and span IDs to response headers
                        addResponseHeaders(exchange);

                        return chain.filter(exchange);
                    } catch (Exception e) {
                        // Log the error and continue the filter chain
                        log.error("Error processing JWT or tracing context", e);
                        return chain.filter(exchange);
                    }
                });
    }

    private Mono<Authentication> letGo(ServerWebExchange exchange, WebFilterChain chain) {
        // Add trace and span IDs to response headers
        addResponseHeaders(exchange);

        // Continue the filter chain without modifying the SecurityContext
        return chain.filter(exchange).then(Mono.empty());
    }

    private void addResponseHeaders(ServerWebExchange exchange) {
        try {
            Span currentSpan = Span.fromContext(Context.current());
            String traceId = currentSpan.getSpanContext().getTraceId();
            String spanId = currentSpan.getSpanContext().getSpanId();
            exchange.getResponse().getHeaders().add("X-TraceId", traceId);
            exchange.getResponse().getHeaders().add("X-SpanId", spanId);
        } catch (Exception e) {
            // Log the error if tracing context is not available
            log.warn("Failed to add tracing headers: " + e.getMessage());
        }
    }
}
