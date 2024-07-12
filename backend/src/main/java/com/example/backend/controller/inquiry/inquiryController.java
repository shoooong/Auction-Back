package com.example.backend.controller.inquiry;

import com.example.backend.dto.inquiry.InquiryDto;
import com.example.backend.dto.inquiry.InquiryListDto;
import com.example.backend.dto.inquiry.InquiryResponseDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.service.inquiry.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/inquiry")
public class inquiryController {

    @Autowired
    private InquiryService inquiryService;

    // 1:1 문의 등록
    @PostMapping("/user/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void createInquiry(@RequestBody InquiryDto inquiryDto,
                              @AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();
        inquiryDto.setUserId(userId);

        inquiryService.createInquiry(inquiryDto);
    }

    // 1:1 문의 조회
    @GetMapping("/inquiryList")
    public List<InquiryDto> getAllInquiryList(@AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();
        List<InquiryDto> inquiryList = inquiryService.getAllInquiryList(userId);
        log.info("조회 완료 {}", inquiryList);
        return inquiryList;
    }

    // 1:1 문의 상세조회
    @GetMapping("/{inquiryId}")
    public InquiryDto getInquiry(@PathVariable Long inquiryId,
                                 @AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();
        InquiryDto inquiryDto = inquiryService.getInquiryById(inquiryId, userId);
        log.info("1:1 문의 상세 조회: {}", inquiryDto);
        return inquiryDto;
    }

    // 1:1 문의 삭제
    @DeleteMapping("/{inquiryId}/delete")
    public void deleteInquiry(@PathVariable Long inquiryId,
                              @AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();
        inquiryService.deleteInquiry(inquiryId, userId);
    }

    // 1:1 문의 답변 등록
    @PostMapping("/inquiryResponseRegistration")
    @ResponseStatus(HttpStatus.CREATED)
    public InquiryResponseDto createInquiryResponse(@RequestBody InquiryResponseDto inquiryResponseDto,
                                                    @AuthenticationPrincipal UserDTO userDTO) {
        if (!userDTO.isRole()) {
            throw new RuntimeException("Only administrators can create responses");
        }

        inquiryResponseDto.setUserId(userDTO.getUserId());
        InquiryResponseDto createInquiryResponse = inquiryService.createInquiryResponse(inquiryResponseDto);
        log.info("답변 등록 완료: {}", createInquiryResponse);
        return createInquiryResponse;
    }

    // 1:1 문의 답변 삭제
    @DeleteMapping("/delete/{responseId}")
    public void deleteInquiryResponse(@PathVariable Long responseId) {
        inquiryService.deleteInquiryResponse(responseId);
    }
}
