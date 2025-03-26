package com.example.productReview.service.handler;

import com.example.productReview.entity.Review;
import com.example.productReview.repository.ReviewRepository;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviewsByProductId(long productId) {
        Span currentSpan = Span.fromContext(Context.current());
        String traceId = currentSpan.getSpanContext().getTraceId();
        String spanId = currentSpan.getSpanContext().getSpanId();
        log.info("getReviewsByProductId traceId: {}, spanId: {}", traceId, spanId);
        log.info("getReviewsByProductId {}", productId);
        return reviewRepository.findByProductId(productId);
    }
}
