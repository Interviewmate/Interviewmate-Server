package org.interviewmate.domain.keyword.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.keyword.model.request.PostAssociateKeywordReq;
import org.interviewmate.domain.keyword.service.KeywordService;
import org.interviewmate.global.util.response.ResponseCode;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "키워드 관련 API")
@RestController
@RequestMapping("/keywords")
@RequiredArgsConstructor
public class KeywordApiController {

    private final KeywordService keywordService;

    @Operation(summary = "키워드 연결  API", description = "유저와 선택한 키워드를 연결")
    @PostMapping("/user")
    public ResponseEntity<ResponseDto<String>> associateKeywordWithUser(@RequestBody PostAssociateKeywordReq postAssociateKeywordReq) {
        return ResponseUtil.SUCCESS(ResponseCode.SUCCESS, keywordService.associateKeywordWithUser(postAssociateKeywordReq));
    }

}
