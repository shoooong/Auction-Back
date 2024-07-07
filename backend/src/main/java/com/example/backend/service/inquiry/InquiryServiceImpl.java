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
    public List<InquiryListDto> getAllInquiryList(Long userId) {
        List<Inquiry> inquiryList = inquiryRepository.findByUser_UserId(userId);
        log.info("Found {} inquiryList for user {}", inquiryList.size(), userId);

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
    public InquiryDto getInquiryById(Long inquiryId, Long userId) {
        Inquiry inquiry = (Inquiry) inquiryRepository.findByInquiryIdAndUser_UserId(inquiryId, userId)
                .orElseThrow(() -> new RuntimeException("해당 문의를 찾을 수 없습니다."));

        List<InquiryResponse> responses = inquiryResponseRepository.findByInquiry_inquiryId(inquiryId);

        List<InquiryResponseDto> responseDtos = responses.stream()
                .map(response -> InquiryResponseDto.builder()
                        .response(response.getResponse())
                        .build())
                .toList();

        return InquiryDto.builder()
                .inquiryId(inquiry.getInquiryId())
                .inquiryTitle(inquiry.getInquiryTitle())
                .inquiryContent(inquiry.getInquiryContent())
                .createdDate(inquiry.getCreateDate())
                .modifyDate(inquiry.getModifyDate())
                .userId(inquiry.getUser().getUserId())
                .response(responseDtos.toString())
                .build();
    }

    @Override
    public void deleteInquiry(long inquiryId, Long userId) {

    }


    // 1:1 문의 등록
    @Override
    public Inquiry createInquiry(Long userId, InquiryDto inquiryDto) {
        Users user = userRepository.findById(userId)
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
    public void deleteInquiry(Long inquiryId, Long userId) {

        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        Long inquiryUserId = inquiry.getUser().getUserId();
        if (!inquiryUserId.equals(userId)) {
            throw new RuntimeException("User is not authorized to delete this inquiry");
        }

        List<InquiryResponse> inquiryResponses = responseRepository.findByInquiry_InquiryId(inquiryId);
        inquiryResponseRepository.deleteAll(inquiryResponses);

        inquiryRepository.deleteById(inquiryId);
        log.info("Deleted inquiry with ID: {}", inquiryId);
    }



    // 1:1 문의 답변 등록
    @Override
    public InquiryResponseDto createInquiryResponse(InquiryResponseDto inquiryResponseDto) {
        Users user = userRepository.findById(inquiryResponseDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Inquiry inquiry = inquiryRepository.findById(inquiryResponseDto.getInquiryId())
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        if (!user.isRole()) {
            throw new RuntimeException("Only administrators can create responses");
        }

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
