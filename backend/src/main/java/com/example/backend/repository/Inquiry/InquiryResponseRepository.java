package com.example.backend.repository.Inquiry;

import com.example.backend.entity.InquiryResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryResponseRepository extends JpaRepository<InquiryResponse, Long> {
    InquiryResponse findByInquiry_InquiryId(Long inquiryId);
}
