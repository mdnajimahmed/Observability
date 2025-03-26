package com.example.productReview.service;

import com.example.productReview.entity.Review;
import com.example.productReview.grpc.reviews.*;
import com.example.productReview.repository.ReviewRepository;
import com.example.productReview.service.handler.ReviewService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@Slf4j
public class ReviewGrpcServiceImpl extends ReviewServiceGrpc.ReviewServiceImplBase{
    private final ReviewService reviewService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public ReviewGrpcServiceImpl(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public void getReview(ReviewRequest request, StreamObserver<ReviewResponse> responseObserver) {
        super.getReview(request, responseObserver);
    }

    @Override
    public void getAllReviewsByProduct(ProductReviewRequest request, StreamObserver<ProductReviewResponse> responseObserver) {
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

    @Override
    public void addReview(AddReviewRequest request, StreamObserver<ReviewResponse> responseObserver) {
        super.addReview(request, responseObserver);
    }

    @Override
    public void updateReview(UpdateReviewRequest request, StreamObserver<ReviewResponse> responseObserver) {
        super.updateReview(request, responseObserver);
    }

    @Override
    public void deleteReview(ReviewRequest request, StreamObserver<DeleteReviewResponse> responseObserver) {
        super.deleteReview(request, responseObserver);
    }
}
