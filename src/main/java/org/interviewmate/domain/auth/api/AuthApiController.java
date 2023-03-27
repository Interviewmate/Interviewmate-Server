package org.interviewmate.domain.auth.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.auth.dto.LoginReq;
import org.interviewmate.domain.auth.dto.LoginRes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 관련 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApiController {

    @Operation(summary = "로그인 API", description = "필요한 정보를 받아 로그인 진행")
    @PostMapping("/login")
    public LoginRes signUp(@RequestBody LoginReq loginReq) {
        return null;
    }

}
