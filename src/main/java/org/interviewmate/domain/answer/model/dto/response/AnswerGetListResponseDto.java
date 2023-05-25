package org.interviewmate.domain.answer.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.interviewmate.domain.answer.model.dto.AnswerInfoDto;

import java.util.List;

@Schema(description = "월별 면접 조회 response dto")
@Getter
@AllArgsConstructor
public class AnswerGetListResponseDto {
    private List<AnswerInfoDto> answerList;
}
