package org.interviewmate.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     *  Error Code
     *  400 : 잘못된 요청
     *  401 : JWT에 대한 오류
     *  403 : 요청한 정보에 대한 권한 없음.
     *  404 : 존재하지 않는 정보에 대한 요청.
     */

    //common error
    NOT_FOUND_DATA(HttpStatus.NOT_FOUND, "해당하는 데이터를 찾을 수 없습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다." ),

    //user error
    EMPTY_KEYWORD(HttpStatus.BAD_REQUEST, "키워드를 입력해주세요."),
    EXIST_NICKNAME(HttpStatus.BAD_REQUEST, "중복되는 닉네임입니다."),

    //interview error
    INTERVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 인터뷰가 없습니다");




    private final HttpStatus code;
    private final String message;
    
}
