package com.example.backend.service.inquiry;

import com.example.backend.dto.inquiry.InquiryDto;
import com.example.backend.dto.inquiry.InquiryListDto;
import com.example.backend.dto.inquiry.InquiryResponseDto;
import com.example.backend.entity.Inquiry;

import java.util.List;

public interface InquiryService {

    // 1:1 문의 등록
    Inquiry createInquiry(InquiryDto inquiryDto);

    // 1:1 문의 조회
    List<InquiryDto> getAllInquiryList(Long userId);

    // 1:1 문의 조회 - 관리자
    List<InquiryDto> getAllInquiryListAdmin();

    // 1:1 문의 상세조회
    InquiryDto getInquiryById(Long inquiryId, Long userId);

    // 1:1 문의 상세조회 - 관리자
    InquiryDto getInquiryByIdForAdmin(Long inquiryId);

    // 1:1 문의 삭제
    void deleteInquiry(final long inquiryId, Long userId);

    void deleteInquiry(Long inquiryId, Long userId);

    // 1:1 문의 답변 등록
    InquiryResponseDto createInquiryResponse(InquiryResponseDto inquiryResponseDto);

    // 1:1 문의 답변 삭제
    void deleteInquiryResponse(final long responseId);
}
