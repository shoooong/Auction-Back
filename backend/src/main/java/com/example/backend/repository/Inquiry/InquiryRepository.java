package com.example.backend.repository.Inquiry;

import com.example.backend.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByUser_UserId(Long userId);

    Optional<Object> findByInquiryIdAndUser_UserId(Long inquiryId, Long userId);

}
