package org.example.expert.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RequestLogContext {
    private final Long userId;
    private final String userRole;
    private final String uri;
    private final String method;
    private final LocalDateTime requestTime;
}

