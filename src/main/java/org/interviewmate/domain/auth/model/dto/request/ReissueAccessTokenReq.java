package org.interviewmate.domain.auth.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "어세스 토큰 재발급 Request", description = "토큰 재발급에 필요한 정보")
@Getter
public class ReissueAccessTokenReq {

    @Schema(description = "이메일", example = "moa.moa.interview@gmail.com")
    private String email;
    @Schema(description = "비밀번호", example = "Moamoa0302!")
    private String password;

}
