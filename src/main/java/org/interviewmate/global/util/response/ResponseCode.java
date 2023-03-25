package org.interviewmate.global.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(200, "성공"),
    FAILURE(201, "에러는 아니지만 실패");

    private final int code;
//    private final HttpStatus status; 이 방법으로 할지 code 만들어서 쓸지
    private final String message;

}
