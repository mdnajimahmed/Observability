package com.example.productAggregator.config;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
public class PostSecurityFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication != null && authentication.getPrincipal() instanceof Jwt)
                .flatMap(authentication -> traceUserEmail(authentication,exchange,chain));
    }

    private Mono<Void> traceUserEmail(Authentication authentication, ServerWebExchange exchange, WebFilterChain chain) {
        try {
            log.info("fetching user email");
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = jwt.getClaimAsString("email");
            Span currentSpan = Span.fromContext(Context.current());
            currentSpan.setAttribute("user.email", email);
            return chain.filter(exchange);
        } catch (Exception e) {
            log.error("Error processing JWT or tracing context", e);
            return chain.filter(exchange);
        }
    }
}