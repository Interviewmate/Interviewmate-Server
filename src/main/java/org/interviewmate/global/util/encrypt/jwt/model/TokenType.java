package org.interviewmate.global.util.encrypt.jwt.model;

import java.time.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {

    ACCESS_TOKEN("access", Duration.ofMinutes(30).toMillis()),
    REFRESH_TOKEN("refresh",Duration.ofDays(7).toMillis());

    private final String name;
    private final long expirationTime;

}
