package com.example.productAggregator.config;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Component
public class TraceIdHeaderFilter implements WebFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(authentication -> {
                    if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
                        String email = jwt.getClaimAsString("email"); // Extract email from JWT

                        // Add email to the current span
//                        Span currentSpan = Span.current();
                        Span currentSpan = Span.fromContext(Context.current());
                        currentSpan.setAttribute("user.email", email);

                        String traceId = currentSpan.getSpanContext().getTraceId();
                        String spanId = currentSpan.getSpanContext().getSpanId();

                        // Add TraceId and SpanId headers to the response
                        exchange.getResponse().getHeaders().add("X-TraceId", traceId);
                        exchange.getResponse().getHeaders().add("X-SpanId", spanId);

                    }
                    return chain.filter(exchange);
                });
    }
}
