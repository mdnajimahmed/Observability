package com.example.productReview.service;

import com.example.productReview.entity.Review;
import com.example.productReview.grpc.reviews.*;
import com.example.productReview.repository.ReviewRepository;
import com.example.productReview.service.handler.ReviewService;
import io.grpc.stub.StreamObserver;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;
//import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.grpc.server.service.GrpcService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@Slf4j
public class ReviewGrpcServiceImpl extends ReviewServiceGrpc.ReviewServiceImplBase{
    private static final io.grpc.Context.Key<String> TraceContextKey = io.grpc.Context.key("trace-id");
    private final ReviewService reviewService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public ReviewGrpcServiceImpl(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public void getAllReviewsByProduct(ProductReviewRequest request, StreamObserver<ProductReviewResponse> responseObserver) {

        Span currentSpan = Span.fromContext(Context.current());
        String traceId = currentSpan.getSpanContext().getTraceId();
        String spanId = currentSpan.getSpanContext().getSpanId();
        log.info("getReviewsByProductId traceId: {}, spanId: {}", traceId, spanId);
        List<Review> reviews = reviewService.getReviewsByProductId(request.getProductId());

        ProductReviewResponse response = ProductReviewResponse.newBuilder()
                .addAllReviews(reviews.stream()
                        .map(this::mapReviewToResponse)
                        .collect(Collectors.toList()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private ReviewResponse mapReviewToResponse(Review review) {
        return ReviewResponse.newBuilder()
                .setReviewId(review.getId())
                .setProductId(review.getProductId())
                .setUserId(review.getUserId())
                .setRating(review.getRating())
                .setComment(review.getComment() != null ? review.getComment() : "")
                .setCreatedAt(toDateTime(review.getCreatedAt()))
                .setUpdatedAt(toDateTime(review.getUpdatedAt()))
                .build();
    }

    private String toDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(formatter) : "";
    }
}
