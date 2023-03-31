package org.interviewmate.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.interviewmate.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {

    private ErrorCode errorCode;

}
