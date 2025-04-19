package org.example.expert.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingUtils {

    private final ObjectMapper objectMapper;

    public void logRequest(RequestLogContext ctx, Object[] args) {
        try {
            String body = objectMapper.writeValueAsString(args);
            log.info("🟡 [REQUEST] [{}] {} {}, userId={}, role={}, body={}",
                    ctx.getRequestTime(), ctx.getMethod(), ctx.getUri(), ctx.getUserId(), ctx.getUserRole(), body);
        } catch (Exception e) {
            log.warn("요청 바디 로깅 실패", e);
        }
    }

    public void logResponse(RequestLogContext ctx, Object result) {
        try {
            String body = objectMapper.writeValueAsString(result);
            log.info("🟢 [RESPONSE] [{}] {} {}, userId={}, result={}",
                    ctx.getRequestTime(), ctx.getMethod(), ctx.getUri(), ctx.getUserId(), body);
        } catch (Exception e) {
            log.warn("응답 바디 로깅 실패", e);
        }
    }
}
