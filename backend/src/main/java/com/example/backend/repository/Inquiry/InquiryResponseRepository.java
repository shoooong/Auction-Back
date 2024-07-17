package com.example.backend.repository.Inquiry;

import com.example.backend.entity.InquiryResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryResponseRepository extends JpaRepository<InquiryResponse, Long> {
    List<InquiryResponse> findByInquiry_InquiryId(Long inquiryId);
}
