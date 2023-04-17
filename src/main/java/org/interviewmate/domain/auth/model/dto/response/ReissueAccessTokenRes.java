package org.interviewmate.domain.auth.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.interviewmate.domain.user.model.User;

@Schema(name = "어세스 토큰 재발급 Response", description = "어세스 토큰 재발급 결과에 대한 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ReissueAccessTokenRes {

    @Schema(description = "유저 식별자", example = "1")
    private Long userId;

    private String accessToken;

    public static ReissueAccessTokenRes of(User user, String accessToken) {
        return ReissueAccessTokenRes.builder()
                .userId(user.getUserId())
                .accessToken(accessToken)
                .build();
    }


}
