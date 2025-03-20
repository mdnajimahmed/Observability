//package com.example.productAggregator.config;
//
//import io.opentelemetry.api.GlobalOpenTelemetry;
//import io.opentelemetry.api.OpenTelemetry;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GlobalOpenTelemetrySetter implements BeanPostProcessor {
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) {
//        if (bean instanceof OpenTelemetry openTelemetry) {
//            GlobalOpenTelemetry.set(openTelemetry);
//        }
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        return bean;
//    }
//
//}