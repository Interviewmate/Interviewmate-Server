package org.interviewmate.domain.user.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Schema(name = "회원 가입 Request", description = "회원 가입에 필요한 유저에 대한 정보")
@Getter
public class PostUserReq {

    @Schema(description = "이메일", example = "moa.moa.interview@gmail.com")
    private String email;
    @Schema(description = "비밀번호", example = "Moamoa0302!")
    private String password;

    @Schema(description = "닉네임", example = "모아모아뀽")
    private String nickName;

    @Schema(description = "직무", example = "서버 개발자")
    private String job;

    @Schema(description = "키워드", example = "{Spring, JPA, Java}")
    private List<String> keyword;

}
