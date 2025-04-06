package com.example.productAggregator;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableWebFlux
public class ProductAggregatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductAggregatorApplication.class, args);
        Thread.startVirtualThread(() -> {
            System.out.println("Is virtual thread? " + Thread.currentThread().isVirtual());
        });
    }
}
