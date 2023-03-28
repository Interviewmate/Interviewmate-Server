package org.interviewmate.domain.user.api;

import static org.interviewmate.global.util.response.ResponseCode.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.user.model.dto.request.PostUserReqDto;
import org.interviewmate.domain.user.model.dto.response.PostUserResDto;
import org.interviewmate.domain.user.service.UserService;
import org.interviewmate.global.util.response.ResponseCode;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 관련 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApiController {

    private final ResponseUtil responseUtil;
    private final UserService userService;

    @Operation(summary = "회원 가입 API", description = "필요한 정보를 받아 회원 가입 진행")
    @PostMapping("/sign-up")
    // todo: valid 처리
    public ResponseDto<PostUserResDto> signUp(@RequestBody PostUserReqDto postUserReqDto) {
        return responseUtil.SUCCESS(SUCCESS ,userService.createUser(postUserReqDto));
    }

}
