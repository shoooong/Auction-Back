package com.example.backend.service.user;

import com.example.backend.dto.mypage.buyHistory.BuyHistoryAllDto;
import com.example.backend.dto.mypage.main.MypageMainDto;
import com.example.backend.dto.mypage.main.ProfileDto;
import com.example.backend.dto.mypage.saleHistory.SaleHistoryDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.dto.user.UserModifyDTO;
import com.example.backend.dto.user.UserRegisterDTO;
import com.example.backend.entity.Users;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.service.OrdersService;
import com.example.backend.service.SalesBiddingService;
import com.example.backend.service.UserCouponService;
import com.example.backend.service.mypage.BookmarkProductService;
import com.example.backend.service.objectstorage.ObjectStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserService {

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;

   private final OrdersService ordersService;
   private final SalesBiddingService salesBiddingService;
   private final UserCouponService userCouponService;
   private final BookmarkProductService bookmarkProductService;

   private final ObjectStorageService objectStorageService;

    public UserDTO entityToDTO(Users user) {
       return new UserDTO(
              user.getUserId(),
              user.getEmail(),
              user.getPassword(),
              user.getGrade(),
              user.getNickname(),
              user.getPhoneNum(),
              user.getProfileImg(),
              user.isSocial(),
              user.isRole());
   }

   public void registerUser(UserRegisterDTO userRegisterDTO, MultipartFile file, boolean isAdmin) {
      if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
         throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
      }

      String imageUrl = "";

      if (file != null && !file.isEmpty()) {
         String bucketName = "push";
         String directoryPath = "shooong/mypage/";

         imageUrl = objectStorageService.uploadFile(bucketName, directoryPath, file);
      }

      Users user = Users.builder()
              .email(userRegisterDTO.getEmail())
              .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
              .nickname(userRegisterDTO.getNickname())
              .phoneNum(userRegisterDTO.getPhoneNum())
              .profileImg(imageUrl)
              .role(isAdmin)
              .build();

      userRepository.save(user);
   }

   public UserDTO getKakaoMember(String accessToken) {

      List<String> kakaoAccountList = getProfileFromKakaoToken(accessToken);
//        String email = kakaoAccountList.get(0);
//
//        Users user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

      Users socialUser = makeSocialUser(kakaoAccountList);

      userRepository.save(socialUser);

      UserDTO userDTO = entityToDTO(socialUser);

      return userDTO;
   }


   /**
    * 카카오 소셜 로그인 - accessToken(카카오 OAuth토큰)으로 카카오 email 및 nickname 가져오기
    */
   public List<String> getProfileFromKakaoToken(String accessToken) {

      String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";

      if (accessToken == null) {
         throw new RuntimeException("Access token is null");
      }
      RestTemplate restTemplate = new RestTemplate();

      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Bearer " + accessToken);
      headers.add("Content-Type", "application/x-www-form-urlencoded");
      HttpEntity<String> entity = new HttpEntity<>(headers);

      UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();

      ResponseEntity<LinkedHashMap> response =
              restTemplate.exchange(
                      uriBuilder.toString(),
                      HttpMethod.GET,
                      entity,
                      LinkedHashMap.class);

      log.info("response: {}", response);

      LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

      log.info("bodyMap: {}", bodyMap);

      LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");
      String kakaoAccountEmail = kakaoAccount.get("email");

      LinkedHashMap<String, String> profile = bodyMap.get("properties");
      String kakaoAccountNickname = profile.get("nickname");

      List<String> kakaoAccountList = new ArrayList<>();
      kakaoAccountList.add(kakaoAccountEmail);
      kakaoAccountList.add(kakaoAccountNickname);

      log.info("kakaoAccount: {}", kakaoAccount);
      log.info("kakaoAccountEmail: {}", kakaoAccountEmail);
      log.info("profile: {}", profile);
      log.info("kakaoAccountNickname: {}", kakaoAccountNickname);

      return kakaoAccountList;
   }

   /**
    * 소셜 로그인 - 해당 이메일을 가진 회원이 없을 경우 회원 등록하기 위해 임시 비밀번호 발급
    */
   public String makeTempPassword() {
      SecureRandom random = new SecureRandom();
      StringBuffer buffer = new StringBuffer();

      for(int i = 0; i < 10; i++) {
         // 임시 비밀번호 생성할 때부터 Math.random이 아닌 SecureRandom를 이용해 암호화
         buffer.append((char) (random.nextInt(55) + 65));
      }

      return buffer.toString();
   }

   public Users makeSocialUser(List<String> kakaoAccountList) {
      String tempPassword = makeTempPassword();
      String email = kakaoAccountList.get(0);
      String nickname = kakaoAccountList.get(1);


      Users user = Users.builder()
              .email(email)
              .password(tempPassword)
              .nickname(nickname)
              .role(false)
              .social(true)
              .build();

      return user;
   }


   /**
    * 회원 정보 수정
    */
   @Transactional
   public void modifyUser(UserModifyDTO userModifyDTO) {
      Users user = userRepository.findByEmail(userModifyDTO.getEmail())
              .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

      String password = userRepository.findPasswordByUserId(user.getEmail());

      // TODO: 비밀번호는 수정 안했을 떄, 다른 방법 생각해보기
      if (userModifyDTO.getPassword() != null) {
         user.updateUser(userModifyDTO, passwordEncoder);
      }
      user.updateUser(password, userModifyDTO);

      userRepository.save(user);
   }

   /**
    * 마이페이지 메인 - 모든 정보 조회
    */
   public MypageMainDto getMyPageInfo(Long userId) {
      Users user = validateUserId(userId);

      ProfileDto profileDto = ProfileDto.builder()
              .profileImg(user.getProfileImg())
              .nickname(user.getNickname())
              .email(user.getEmail())
              .grade(user.getGrade())
              .build();

      Long couponCount = userCouponService.getValidCouponCount(userId);
      BuyHistoryAllDto buyHistoryAllDto = ordersService.getRecentBuyHistory(userId);
      SaleHistoryDto saleHistoryDto = salesBiddingService.getRecentSaleHistory(userId);

      return MypageMainDto.builder()
              .profileDto(profileDto)
              .couponCount(couponCount)
              .buyHistoryAllDto(buyHistoryAllDto)
              .saleHistoryDto(saleHistoryDto)
              .bookmarkProductsDto(bookmarkProductService.getLatestBookmarkProducts(userId))
              .build();
   }

   /**
    * 존재하는 회원인지 확인
    */
   public Users validateUserId(Long userId) {
      return userRepository.findById(userId)
              .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
   }
}
