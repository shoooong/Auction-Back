package com.example.backend.service.inquiry;

import com.example.backend.dto.inquiry.InquiryDto;
import com.example.backend.dto.inquiry.InquiryListDto;
import com.example.backend.dto.inquiry.InquiryResponseDto;
import com.example.backend.entity.Inquiry;
import com.example.backend.entity.InquiryResponse;
import com.example.backend.entity.Users;
import com.example.backend.repository.Inquiry.InquiryRepository;
import com.example.backend.repository.Inquiry.InquiryResponseRepository;
import com.example.backend.repository.User.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class InquiryServiceImpl implements InquiryService{

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InquiryResponseRepository responseRepository;
    @Autowired
    private InquiryResponseRepository inquiryResponseRepository;


    // 1:1 문의 조회
    @Override
    public List<InquiryListDto> getAllInquiryList(){
        List<Inquiry> inquiryList = inquiryRepository.findAll();
        log.info("Found {} inquiryList", inquiryList.size());

        return inquiryList.stream()
                .map(inquiry -> new InquiryListDto(
                        inquiry.getInquiryId(),
                        inquiry.getInquiryTitle(),
                        inquiry.getInquiryContent(),
                        inquiry.getCreateDate(),
                        inquiry.getModifyDate(),
                        inquiry.getUser().getUserId()
                ))
                .collect(Collectors.toList());
    }

    // 1:1 문의 상세 조회
    @Override
    public InquiryDto getInquiryById(Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("해당 문의를 찾을 수 없습니다."));

        List<InquiryResponse> responses = inquiryResponseRepository.findByInquiry_inquiryId(inquiryId);

        List<InquiryResponseDto> responseDtos = responses.stream()
                .map(response -> InquiryResponseDto.builder()
                        .responseId(response.getResponseId())
                        .inquiryId(response.getInquiry().getInquiryId())
                        .userId(response.getUser().getUserId())
                        .createDate(response.getCreateDate())
                        .response(response.getResponse())
                        .build())
                .collect(Collectors.toList());

        return InquiryDto.builder()
                .inquiryId(inquiry.getInquiryId())
                .inquiryTitle(inquiry.getInquiryTitle())
                .inquiryContent(inquiry.getInquiryContent())
                .createdDate(inquiry.getCreateDate())
                .modifyDate(inquiry.getModifyDate())
                .userId(inquiry.getUser().getUserId())
                .responses(responseDtos)  // InquiryResponseDto 리스트 추가
                .build();
    }

    // 1:1 문의 등록
    @Override
    public Inquiry createInquiry(InquiryDto inquiryDto) {
        Users user = userRepository.findById(inquiryDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Inquiry inquiry = new Inquiry();
        inquiry.setInquiryTitle(inquiryDto.getInquiryTitle());
        inquiry.setInquiryContent(inquiryDto.getInquiryContent());
        inquiry.setUser(user);

        Inquiry savedInquiry = inquiryRepository.save(inquiry);
        log.info("Saved inquiry: {}", savedInquiry);

        return savedInquiry;
    }

    // 1:1 문의 삭제
    @Override
    public void deleteInquiry(final long inquiryId) {
        final List<InquiryResponse> inquiryResponses = responseRepository.findByInquiry_inquiryId(inquiryId);
        inquiryResponseRepository.deleteAll(inquiryResponses);
        inquiryRepository.deleteById(inquiryId);
    }

    // 1:1 문의 답변 등록
    @Override
    public InquiryResponseDto createInquiryResponse(InquiryResponseDto inquiryResponseDto) {
        Users user = userRepository.findById(inquiryResponseDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Inquiry inquiry = inquiryRepository.findById(inquiryResponseDto.getInquiryId())
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        InquiryResponse inquiryResponse = InquiryResponse.builder()
                .user(user)
                .inquiry(inquiry)
                .response(inquiryResponseDto.getResponse())
                .build();

        InquiryResponse savedInquiryResponse = inquiryResponseRepository.save(inquiryResponse);
        log.info("답변 작성 완료: {}", savedInquiryResponse);

        return new InquiryResponseDto(
                savedInquiryResponse.getResponseId(),
                savedInquiryResponse.getInquiry().getInquiryId(),
                savedInquiryResponse.getUser().getUserId(),
                savedInquiryResponse.getCreateDate(),
                savedInquiryResponse.getModifyDate(),
                savedInquiryResponse.getResponse()
        );
    }

    // 1:1 문의 답변 삭제
    @Override
    public void deleteInquiryResponse(final long inquiryResponseId) {
        inquiryResponseRepository.deleteById(inquiryResponseId);
    }

}
