package org.interviewmate.domain.user.api;

import static org.interviewmate.global.util.response.ResponseCode.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.user.model.dto.request.PostUserReqDto;
import org.interviewmate.domain.user.model.dto.response.PostUserResDto;
import org.interviewmate.domain.user.model.dto.response.FindUserInformationOutDto;
import org.interviewmate.domain.user.service.UserService;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 관련 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @Operation(summary = "회원 가입 API", description = "필요한 정보를 받아 회원 가입 진행")
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto<PostUserResDto>> signUp(@RequestBody @Valid PostUserReqDto postUserReqDto) {

        PostUserResDto postUserResDto = userService.createUser(postUserReqDto);
        return ResponseUtil.SUCCESS(CREATED, postUserResDto);

    }

    @Operation(summary = "회원 정보 불러오기 API", description = "회원에 대한 정보를 불러온다.")
    @GetMapping("/info/{userId}")
    public ResponseEntity<ResponseDto<FindUserInformationOutDto>> signUp(@PathVariable Long userId) {

        FindUserInformationOutDto findUserInfo = userService.getUserInfo(userId);
        return ResponseUtil.SUCCESS(CREATED, findUserInfo);

    }


}
