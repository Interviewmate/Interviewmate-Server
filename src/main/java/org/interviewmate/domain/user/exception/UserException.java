package org.interviewmate.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;

@Getter
public class UserException extends CustomException {

    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }

}
