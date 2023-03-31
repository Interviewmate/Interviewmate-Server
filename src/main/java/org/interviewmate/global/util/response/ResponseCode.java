package org.interviewmate.global.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /**
     * Success Code
     * 200 : 요청 성공
     * 201 : 요청으로 인해 새로운 리소스 생성
     * 204 : 요청에 성공했으나 데이터는 없음
     */

    SUCCESS(HttpStatus.OK, "요청이 완료 되었습니다."),
    CREATED(HttpStatus.CREATED, "생성이 완료되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "요청에 대한 정보가 없습니다."),
    FAILURE_ALREADY_REPORTED(HttpStatus.ALREADY_REPORTED, "에러는 아니지만 실패");

    private final HttpStatus code;
    private final String message;

}
