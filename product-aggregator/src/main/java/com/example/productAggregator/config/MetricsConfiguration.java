package com.example.productAggregator.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;


import java.util.List;

public class MetricsConfiguration {
    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    public MeterFilter addServiceNameTag() {
        return MeterFilter.commonTags(List.of(Tag.of("service", serviceName)));
    }
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> {
            registry.config().meterFilter(addServiceNameTag());
        };
    }
}
