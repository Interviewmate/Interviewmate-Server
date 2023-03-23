package org.interviewmate.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Schema(name = "로그인 Request", description = "로그인에 필요한 정보")
@Getter
public class LoginReq {

    @Schema(description = "이메일", example = "moa.moa.interview@gmail.com")
    private String email;
    @Schema(description = "비밀번호", example = "Moamoa0302!")
    private String password;

}
