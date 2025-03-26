package com.example.productReview.service.handler;

import com.example.productReview.entity.Review;
import com.example.productReview.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviewsByProductId(long productId) {
        return reviewRepository.findByProductId(productId);
    }
}
