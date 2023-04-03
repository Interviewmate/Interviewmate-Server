package org.interviewmate.domain.auth.api;

import static org.interviewmate.global.util.response.ResponseCode.SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.auth.dto.request.LoginReq;
import org.interviewmate.domain.auth.dto.reponse.LoginRes;
import org.interviewmate.domain.auth.service.AuthService;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.interviewmate.infra.email.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 관련 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final EmailService emailService;
    private final AuthService authService;

    @Operation(summary = "로그인 API", description = "필요한 정보를 받아 로그인 진행")
    @PostMapping("/login")
    public ResponseDto<LoginRes> signUp(@RequestBody LoginReq loginReq) {
        return ResponseUtil.SUCCESS(SUCCESS, authService.signUp(loginReq));
    }

    @Operation(summary = "메일 인증 코드 발송 API", description = "회원 가입 시 이메일 인증 코드 발송")
    @Parameter(name = "toEmail", description = "이메일", example = "parkrootseok@gmail.com")
    @GetMapping("/email")
    public ResponseDto<String> sendAuthenticationCode(@RequestParam @Email String toEmail) {

        emailService.sendEmail(toEmail);
        return ResponseUtil.SUCCESS(SUCCESS, "이메일을 확인하세요.");

    }

    @Operation(summary = "메일 인증 코드 검증 API", description = "발송한 인증 코드 검증")
    @Parameters({
            @Parameter(name = "email", description = "이메일", example = "parkrootseok@gmail.com"),
            @Parameter(name = "code", description = "인증 코드", example = "53085")
    })
    @GetMapping("/authCode")
    private ResponseDto<String> verifyAuthenticationCode(@RequestParam @Email String email, @RequestParam String code) {

        if(emailService.verifyEmailCode(email, code)) {
            return ResponseUtil.SUCCESS(SUCCESS, "인증 완료.");
        }

        return ResponseUtil.SUCCESS(SUCCESS, "인증 실패.");

    }

}
