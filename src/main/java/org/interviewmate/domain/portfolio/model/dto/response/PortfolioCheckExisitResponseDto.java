package org.interviewmate.domain.portfolio.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema(description = "포트폴리오 유무 확인 dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PortfolioCheckExisitResponseDto {
    @Schema(description = "포트폴리오 유무", example = "true (포트폴리오 있음)")
    private Boolean isExist;
}
