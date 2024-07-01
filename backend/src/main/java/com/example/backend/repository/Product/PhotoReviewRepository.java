package com.example.backend.repository.Product;

import com.example.backend.entity.PhotoReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoReviewRepository extends JpaRepository<PhotoReview,Long> {
}
