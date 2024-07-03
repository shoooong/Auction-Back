package com.example.backend.controller.inquiry;

import com.example.backend.dto.inquiry.InquiryDto;
import com.example.backend.dto.inquiry.InquiryListDto;
import com.example.backend.dto.inquiry.InquiryResponseDto;
import com.example.backend.entity.Inquiry;
import com.example.backend.service.inquiry.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Log4j2
public class inquiryController {

    @Autowired
    private InquiryService inquiryService;


    // 1:1 문의 조회
    @GetMapping("/inquiryList")
    public List<InquiryListDto> getAllInquiryList() {
        List<InquiryListDto> inquiryList = inquiryService.getAllInquiryList();
        log.info("조회 완료 {}", inquiryList);
        return inquiryList;
    }

    // 1:1 문의 상세 조회
    @GetMapping("/inquiry/{inquiryId}")
    public InquiryDto getInquiry(@PathVariable Long inquiryId) {
        InquiryDto inquiryDto = inquiryService.getInquiryById(inquiryId);
        log.info("1:1 문의 상세 조회: {}", inquiryDto);
        return inquiryDto;
    }

    // 1:1 문의 등록
    @PostMapping("/inquiryRegistration")
    @ResponseStatus(HttpStatus.CREATED)
    public void createInquiry(@RequestBody InquiryDto inquiryDto) {

        Inquiry createInquiry = inquiryService.createInquiry(inquiryDto);
        log.info("1:1문의 등록 완료{}", createInquiry);
    }

    // 1:1 문의 삭제
    @DeleteMapping("/deleteInquiry/{inquiryId}")
    public void deleteInquiry(@PathVariable Long inquiryId) {
        inquiryService.deleteInquiry(inquiryId);
    }


    // 1:1 문의 답변 등록
    @PostMapping("/inquiryResponseRegistration")
    @ResponseStatus(HttpStatus.CREATED)
    public InquiryResponseDto createInquiryResponse(@RequestBody InquiryResponseDto inquiryResponseDto) {
        InquiryResponseDto createInquiryResponse = inquiryService.createInquiryResponse(inquiryResponseDto);
        log.info("답변 등록 완료: {}", createInquiryResponse);
        return createInquiryResponse;
    }

    // 1:1 문의 답변 삭제
    @DeleteMapping("/deleteInquiryResponse/{responseId}")
    public void deleteInquiryResponse(@PathVariable Long responseId) {
        inquiryService.deleteInquiryResponse(responseId);
    }
}
