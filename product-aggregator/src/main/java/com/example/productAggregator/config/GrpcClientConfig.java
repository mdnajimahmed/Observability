package com.example.productAggregator.config;

import com.example.productReview.grpc.reviews.ReviewServiceGrpc;
import io.grpc.ClientInterceptor;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.grpc.v1_6.GrpcTelemetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GlobalClientInterceptor;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class GrpcClientConfig {
    @Bean
    public GrpcTelemetry grpcTelemetry(OpenTelemetry openTelemetry) {
        return GrpcTelemetry.create(openTelemetry);
    }
    @Bean
    @GlobalClientInterceptor
    public ClientInterceptor grpcTracingInterceptor(GrpcTelemetry grpcTelemetry) {
        return grpcTelemetry.newClientInterceptor();
    }

    @Bean
    ReviewServiceGrpc.ReviewServiceStub stub(GrpcChannelFactory channels) {
        return ReviewServiceGrpc.newStub(channels.createChannel("local"));
    }
}
