package org.interviewmate.domain.auth.api;

import static org.interviewmate.global.util.response.ResponseCode.SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.auth.model.dto.request.LoginReq;
import org.interviewmate.domain.auth.model.dto.request.ReissueAccessTokenReq;
import org.interviewmate.domain.auth.model.dto.response.LoginRes;
import org.interviewmate.domain.auth.model.dto.response.ReissueAccessTokenRes;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.service.UserService;
import org.interviewmate.global.util.encrypt.jwt.service.JwtService;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.interviewmate.infra.email.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 관련 API")
@RestController
@Validated
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final EmailService emailService;
    private final UserService userService;
    private final JwtService jwtService;

    @Operation(summary = "닉네임 중복 검사 API", description = "사용하고 싶은 닉네임을 받아 중복 검사")
    @GetMapping("/check")
    @Parameter(name = "nickName", example = "모아모아뀽")
    public ResponseEntity<ResponseDto<String>> checkNickname(
            @RequestParam @Pattern(regexp = "^[가-힣\\S]*$", message = "공백을 제거하세요.") String nickName
    ) {

        if (!userService.checkNickname(nickName)) {
            return ResponseUtil.FAILURE(SUCCESS, "사용할 수 없는 닉네입입니다.");
        }

        return ResponseUtil.SUCCESS(SUCCESS, "사용할 수 있는 닉네입입니다.");

    }

    @Operation(summary = "로그인 API", description = "필요한 정보를 받아 로그인 진행")
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginRes>> login(@RequestBody @Valid LoginReq loginReq) {

        User user = userService.login(loginReq);
        return ResponseUtil.SUCCESS(SUCCESS, jwtService.issueTokenByLogin(user));

    }

    @Operation(summary = "어세스 토큰 재발급 API", description = "어세스 토큰 만료시 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<ResponseDto<ReissueAccessTokenRes>> reissueAccessToken(
            @RequestBody ReissueAccessTokenReq reissueAccessTokenReq
    ) {
        return ResponseUtil.SUCCESS(SUCCESS, jwtService.reissueAccessToken(reissueAccessTokenReq));
    }

    @Operation(summary = "메일 인증 코드 발송 API", description = "회원 가입 시 이메일 인증 코드 발송")
    @Parameter(name = "toEmail", description = "이메일", example = "parkrootseok@gmail.com")
    @GetMapping("/email")
    public ResponseEntity<ResponseDto<String>> sendAuthenticationCode(@RequestParam @Email String toEmail) {

        emailService.sendEmail(toEmail);
        return ResponseUtil.SUCCESS(SUCCESS, "이메일을 확인하세요.");

    }

    @Operation(summary = "메일 인증 코드 검증 API", description = "발송한 인증 코드 검증")
    @Parameters({
            @Parameter(name = "email", description = "이메일", example = "parkrootseok@gmail.com"),
            @Parameter(name = "code", description = "인증 코드", example = "53085")
    })
    @GetMapping("/authCode")
    public ResponseEntity<ResponseDto<String>> verifyAuthenticationCode(
            @RequestParam @Email String email,
            @RequestParam String code
    ) {

        if(emailService.verifyEmailCode(email, code)) {
            return ResponseUtil.SUCCESS(SUCCESS, "인증 완료");
        }
        return ResponseUtil.FAILURE(SUCCESS, "인증 실패");

    }

}
