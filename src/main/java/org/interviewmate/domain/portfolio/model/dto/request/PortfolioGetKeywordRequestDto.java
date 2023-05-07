package org.interviewmate.domain.portfolio.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Schema(description = "면접 생성 request dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioGetKeywordRequestDto {
    @Schema(description = "사용자 id")
    @NotNull
    private Long userId;
}
