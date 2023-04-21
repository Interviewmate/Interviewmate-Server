package org.interviewmate.domain.user.api;

import static org.interviewmate.global.util.response.ResponseCode.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.keyword.service.KeywordService;
import org.interviewmate.domain.user.model.dto.request.PostAssociateKeywordReq;
import org.interviewmate.domain.user.model.dto.request.PostUserReqDto;
import org.interviewmate.domain.user.model.dto.response.PostUserResDto;
import org.interviewmate.domain.user.service.UserService;
import org.interviewmate.global.util.response.ResponseCode;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
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
    private final KeywordService keywordService;

    @Operation(summary = "회원 가입 API", description = "필요한 정보를 받아 회원 가입 진행")
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto<PostUserResDto>> signUp(@RequestBody @Valid PostUserReqDto postUserReqDto) {

        PostUserResDto postUserResDto = userService.createUser(postUserReqDto);
        return ResponseUtil.SUCCESS(CREATED, postUserResDto);

    }

    @Operation(summary = "키워드 연결  API", description = "유저와 선택한 키워드를 연결")
    @PostMapping("/keyword")
    public ResponseEntity<ResponseDto<String>> associateUserWithKeyword(@RequestBody PostAssociateKeywordReq postAssociateKeywordReq) {
        return ResponseUtil.SUCCESS(ResponseCode.SUCCESS, keywordService.associateUserWithKeyword(postAssociateKeywordReq));
    }

}
