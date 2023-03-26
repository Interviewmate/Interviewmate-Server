package org.interviewmate.global.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(HttpStatus.OK, "성공"),
    FAILURE_ALREADY_REPORTED(HttpStatus.ALREADY_REPORTED, "에러는 아니지만 실패");

    private final HttpStatus code;
    private final String message;

}
