package com.example.backend.security.filter;

import com.example.backend.Jwt.util.JWTUtil;
import com.example.backend.dto.user.UserDTO;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    /**
     * 체크하지 않을 경로나 메서드(GET/POST) 지정
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        // 1) Ajax 통신 시 Preflight로 전송되는 OPTIONS 방식 경로 제외
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String path = request.getRequestURI();

        log.info("check uri............" + path);

        // 2) /user/ 로그인 호출 경로 제외
        // TODO: 로그인 호출 경로 재설정
        if (path.startsWith("/user/")) {
            return true;
        }

        // 3) 이미지 조회 경로 제외
        // TODO: 클라우드 DB 이미지 업로드 성공 시 경로 설정

        return false;
    }

    // 모든 요청에 대해서 체크
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("------------------JWTCheckFilter...-----------------");

        String authHeaderStr = request.getHeader("Authorization");

        try {
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            log.info("JWT claims: " + claims);

            /**
             * 접근 권한별 처리를 위한 인증 컨텍스트 설정
             */
            String email = (String) claims.get("email");
            String password = (String) claims.get("password");
            int grade = (int) claims.get("grade");
            String nickname = (String) claims.get("nickname");
            String phone = (String) claims.get("phone");
            String defaultAddr = (String) claims.get("defaultAddr");
            Boolean social = (Boolean) claims.get("social");
            Boolean role = (Boolean) claims.get("role");

            UserDTO userDTO = new UserDTO(email, password, grade, nickname, phone, defaultAddr, social.booleanValue(), role);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDTO, password, userDTO.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("JWT Check Error: " + e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }
    }
}
