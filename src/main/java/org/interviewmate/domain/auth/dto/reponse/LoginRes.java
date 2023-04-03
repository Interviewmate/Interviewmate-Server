package org.interviewmate.domain.auth.dto.reponse;

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
    @Schema(description = "Access 토큰", example = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJpYXQiOjE2Nzk1NjYzOTEsImV4cCI6MTY3OTU2ODE5MSwidXNlcklkeCI6MjB9.-Ji4Cw5S8qGbCrNs9MlEyqACEIYH6qZKSsJRobFzY_w")
    private String accessToken;
    @Schema(description = "Refresh 토큰", example = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJpYXQiOjE2Nzk1NjYzOTEsImV4cCI6MTY4MDE3MTE5MX0.cRQqbqLYsbVfYUMj7WQd-IlhMl_-pEC-BOYrIA0TNPc")
    private String refreshToken;

    public static LoginRes of(User user) {
        return LoginRes.builder()
                .userId(user.getUserId())
                .accessToken(user.getAccessToken())
                .refreshToken(user.getRefreshToken())
                .build();
    }


}
