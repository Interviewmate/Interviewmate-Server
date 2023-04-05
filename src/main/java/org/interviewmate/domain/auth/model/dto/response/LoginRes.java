package org.interviewmate.domain.auth.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.interviewmate.domain.user.model.User;

@Schema(name = "로그인 Response", description = "로그인 결과에 대한 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class LoginRes {

    @Schema(description = "유저 식별자", example = "1")
    private Long userId;

    private String accessToken;

    public static LoginRes of(User user, String accessToken) {
        return LoginRes.builder()
                .userId(user.getUserId())
                .accessToken(accessToken)
                .build();
    }


}
