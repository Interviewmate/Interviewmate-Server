package org.interviewmate.domain.auth.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.auth.dto.request.LoginReq;
import org.interviewmate.domain.auth.dto.reponse.LoginRes;
import org.interviewmate.infra.email.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 관련 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final EmailService emailService;

    @Operation(summary = "로그인 API", description = "필요한 정보를 받아 로그인 진행")
    @PostMapping("/login")
    public LoginRes signUp(@RequestBody LoginReq loginReq) {
        return null;
    }

    @Operation(summary = "이메일 인증 API", description = "회원 가입 시 이메일 인증 진행")
    @GetMapping("/email")
    public String AuthenticateEmail(@Parameter(description = "이메일", example = "parkrootseok@gmail.com") @Email String email) {

       return emailService.authenticateEmail(email);

    }
}
