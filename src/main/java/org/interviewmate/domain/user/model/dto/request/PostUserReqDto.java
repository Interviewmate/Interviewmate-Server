package org.interviewmate.domain.user.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.user.model.Job;
import org.interviewmate.domain.user.model.User;

@Schema(name = "회원 가입 Request", description = "회원 가입에 필요한 유저에 대한 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUserReqDto {

    @Email(message = "올바르지 않은 이메일 형식입니다.")
    @Schema(description = "이메일", example = "moa.moa.interview@gmail.com")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%&*?])[A-Za-z\\d!@#$%&*?]{8,}$", message = "최소 1개의 문자, 숫자, 기호를 조합하여 8자 이상을 사용하세요.")
    @Schema(description = "비밀번호", example = "Moamoa0302!")
    private String password;

    @Pattern(regexp = "^[가-힣\\S]*$", message = "공백을 제거하세요.")
    @NotBlank(message = "닉네임을 입력하세요.")
    @Schema(description = "닉네임", example = "모아모아뀽")
    private String nickName;

    @NotBlank(message = "직무를 선택하세요.")
    @Schema(description = "직무", example = "SERVER", allowableValues = {"SERVER, CLIENT, DATA_SCIENTIST, DATA_ANALYST, AI_ENGINEER, AI_RESEARCHER"})
    private String job;

    public static User toEntity(PostUserReqDto postUserReqDto) {
        return User.builder()
                .email(postUserReqDto.getEmail())
                .nickName(postUserReqDto.getNickName())
                .job(Job.valueOf(postUserReqDto.getJob()))
                .build();
    }

}
