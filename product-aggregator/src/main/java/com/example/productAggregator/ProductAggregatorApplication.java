package com.example.productAggregator;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ProductAggregatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProductAggregatorApplication.class, args);
	}
//	@Bean
//	public WebClient webClient(WebClient.Builder webClientBuilder, ObservationRegistry observationRegistry) {
//		return webClientBuilder
//				.baseUrl("http://example.com") // Your base URL
//				.apply(o -> o.observationRegistry(observationRegistry)) // Apply ObservationRegistry
//				.build();
//	}
}
