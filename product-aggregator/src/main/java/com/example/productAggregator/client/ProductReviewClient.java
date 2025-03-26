package com.example.productAggregator.client;

import com.example.productReview.grpc.reviews.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
@Slf4j
public class ProductReviewClient {

    @GrpcClient("review-service")
    private ReviewServiceGrpc.ReviewServiceStub reactiveStub;

    public Flux<ReviewResponse> getReviewsByProductId(Long productId) {
        return Flux.<ReviewResponse>create(sink -> {
            reactiveStub.getAllReviewsByProduct(
                    ProductReviewRequest.newBuilder().setProductId(productId).build(),
                    new StreamObserver<>() {
                        @Override
                        public void onNext(ProductReviewResponse value) {
                            value.getReviewsList().forEach(sink::next);
                        }

                        @Override
                        public void onError(Throwable t) {
                            sink.error(new RuntimeException(
                                    "Failed to get product reviews: " + statusFromThrowable(t), t));
                        }

                        @Override
                        public void onCompleted() {
                            sink.complete();
                        }
                    });
        }).onErrorMap(this::mapGrpcError);
    }

    private Throwable mapGrpcError(Throwable t) {
        if (t instanceof StatusRuntimeException) {
            Status status = ((StatusRuntimeException) t).getStatus();
            return new RuntimeException(
                    "gRPC error: " + status.getCode() + " - " + status.getDescription());
        }
        return t;
    }
    private String statusFromThrowable(Throwable t) {
        if (t instanceof StatusRuntimeException) {
            Status status = ((StatusRuntimeException) t).getStatus();
            return status.getCode() + ": " + status.getDescription();
        }
        return t.getMessage();
    }
}