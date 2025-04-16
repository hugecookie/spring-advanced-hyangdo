package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Slf4j
@Component
public class AdminLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String url = request.getRequestURI();
        String method = request.getMethod();
        String userRole = (String) request.getAttribute("userRole");
        Long userId = (Long) request.getAttribute("userId");

        // ✅ 어드민만 로깅
        if ("ADMIN".equalsIgnoreCase(userRole)) {
            log.info("📝 [ADMIN 접근 기록] 사용자 ID: {}, 요청 URL: {}, HTTP Method: {}, 시간: {}",
                    userId, url, method, LocalDateTime.now());
        }

        return true;
    }
}

