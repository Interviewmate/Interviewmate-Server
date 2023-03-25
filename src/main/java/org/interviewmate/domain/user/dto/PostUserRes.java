package org.interviewmate.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "회원 가입 Response", description = "회원 가입을 완료한 유저에 대한 정보")
@Getter
public class PostUserRes {

    @Schema(description = "유저 식별자", example = "1")
    private Long userId;
    @Schema(description = "이메일", example = "moa.moa.interview@gmail.com")
    private String email;
    @Schema(description = "비밀번호", example = "Moamoa0302!")
    private String password;

}