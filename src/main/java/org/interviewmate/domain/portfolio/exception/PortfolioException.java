package org.interviewmate.domain.portfolio.exception;

import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;

public class PortfolioException extends CustomException {
    public PortfolioException(ErrorCode errorCode) {
        super(errorCode);
    }
}
