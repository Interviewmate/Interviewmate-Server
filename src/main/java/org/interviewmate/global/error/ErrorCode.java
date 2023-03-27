package org.interviewmate.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND_DATA(HttpStatus.NOT_FOUND, "해당하는 데이터를 찾을 수 없습니다");

    private final HttpStatus code;
    private final String message;
}
