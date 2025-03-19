//package com.example.productAggregator.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.opentelemetry.api.OpenTelemetry;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.reactive.function.client.support.WebClientAdapter;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.service.invoker.HttpServiceProxyFactory;
//import io.opentelemetry.instrumentation.spring.webflux;
//
//@Configuration
//public class WebConfig {
//    private final SpringWebfluxTelemetry webfluxTelemetry;
//    private final SpringWebfluxServerTelemetry webfluxServerTelemetry;
//
//    public WebConfig(OpenTelemetry openTelemetry) {
//        this.webfluxTelemetry = SpringWebfluxTelemetry.builder(openTelemetry).build();
//    }
//
//    // Adds instrumentation to WebClients
//    @Bean
//    public WebClient.Builder webClientBuilder() {
//        WebClient webClient = WebClient.create();
//        return webClient.mutate().filters(webfluxTelemetry::addClientTracingFilter);
//    }
//
//
//    // Adds instrumentation to Webflux server
//    @Bean
//    public WebFilter webFilter() {
//        return webfluxTelemetry.createWebFilterAndRegisterReactorHook();
//    }
//
//    @Bean
//    WebClient webClient(ObjectMapper objectMapper , WebClient.Builder webclient) {
//        return webclient
//                .baseUrl("http://client-service:8080")
//                .build();
//    }
//
//
//
//}