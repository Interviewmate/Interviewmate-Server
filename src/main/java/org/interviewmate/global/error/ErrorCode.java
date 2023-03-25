package org.interviewmate.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND_DATA(404, "해당하는 데이터를 찾을 수 없습니다");

    private final int status; //이것도 HttpStatus로 할지 정해야함
    private final String message;
}
