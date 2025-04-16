package org.example.expert.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AdminApiLoggingAspect {

    private final ObjectMapper objectMapper;

    @Around("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..)) || " +
            "execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    public Object logAdminApi(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String uri = request.getRequestURI();
        String method = request.getMethod();
        Long userId = (Long) request.getAttribute("userId");
        LocalDateTime now = LocalDateTime.now();

        // 요청 바디 (args → JSON 변환)
        String requestBody = objectMapper.writeValueAsString(joinPoint.getArgs());

        log.info("🟡 [ADMIN API 요청] 시간: {}, URL: {}, 메서드: {}, 사용자 ID: {}, 요청 바디: {}",
                now, uri, method, userId, requestBody);

        Object result = joinPoint.proceed(); // 실제 메서드 실행

        // 응답 바디
        String responseJson = objectMapper.writeValueAsString(result);
        log.info("🟢 [ADMIN API 응답] URL: {}, 사용자 ID: {}, 응답 바디: {}", uri, userId, responseJson);

        return result;
    }
}