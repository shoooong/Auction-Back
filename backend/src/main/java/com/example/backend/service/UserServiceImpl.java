package com.example.backend.service;

import com.example.backend.dto.user.UserDTO;
import com.example.backend.dto.user.UserModifyDTO;
import com.example.backend.dto.user.UserRegisterDTO;
import com.example.backend.entity.User;
import com.example.backend.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRegisterDTO userRegisterDTO, boolean isAdmin) {
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // TODO: 이후 주소 api 호출 기능 구현되면, 회원가입 시 기본 배송지 입력 받도록 추가
        User user = User.builder()
                .email(userRegisterDTO.getEmail())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .nickname(userRegisterDTO.getNickname())
                .phone(userRegisterDTO.getPhone())
                .role(isAdmin)
                .build();

        userRepository.save(user);
    }

    @Override
    public UserDTO getKakaoMember(String accessToken) {

        List<String> kakaoAccountList = getProfileFromKakaoToken(accessToken);
        String email = kakaoAccountList.get(0);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            UserDTO userDTO = entityToDTO(user.get());
            return userDTO;
        }

        User socialUser = makeSocialUser(kakaoAccountList);

        userRepository.save(socialUser);

        UserDTO userDTO = entityToDTO(socialUser);

        return userDTO;
    }


    /**
     * 카카오 소셜 로그인 - accessToken(카카오 OAuth토큰)으로 카카오 email 및 nickname 가져오기
     */
    private List<String> getProfileFromKakaoToken(String accessToken) {

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
    @Override
    public String makeTempPassword() {
        SecureRandom random = new SecureRandom();
        StringBuffer buffer = new StringBuffer();

        for(int i = 0; i < 10; i++) {
            // 임시 비밀번호 생성할 때부터 Math.random이 아닌 SecureRandom를 이용해 암호화
            buffer.append((char) (random.nextInt(55) + 65));
        }

        return buffer.toString();
    }

    @Override
    public User makeSocialUser(List<String> kakaoAccountList) {
        String tempPassword = makeTempPassword();
        String email = kakaoAccountList.get(0);
        String nickname = kakaoAccountList.get(1);


        User user = User.builder()
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
    @Override
    public void modifyUser(UserModifyDTO userModifyDTO) {
        Optional<User> optUser = userRepository.findByEmail(userModifyDTO.getEmail());

        User user = optUser.orElseThrow();

        user.changePw(passwordEncoder.encode(userModifyDTO.getPassword()));
        user.changeNickname(userModifyDTO.getNickname());

        userRepository.save(user);
    }
}
