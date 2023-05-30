package org.interviewmate.domain.question.model.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionCreateResponseDto {
    private String question;
    private String answer;
    private String keyword;
}
