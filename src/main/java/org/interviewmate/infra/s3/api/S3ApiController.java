package org.interviewmate.infra.s3.api;

import static org.interviewmate.global.util.response.ResponseCode.SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.interviewmate.infra.s3.model.dto.request.GetPreSignedUrlReq;
import org.interviewmate.infra.s3.model.dto.response.GetPreSignedUrlRes;
import org.interviewmate.infra.s3.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "파일 관련 API")
@RestController
@Validated
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3ApiController {

    private final S3Service s3Service;

    @Operation(summary = "객체 저장", description = "객체(portfolio, interview) 등을 저장하기 위해 미리 서명된 주소를 받는다.")
    @PostMapping("/pre-signed")
    public ResponseEntity<ResponseDto<GetPreSignedUrlRes>> saveObject(@RequestBody GetPreSignedUrlReq getPreSignedUrlReq) {
        return ResponseUtil.SUCCESS(
                SUCCESS,
                s3Service.saveObject(
                        getPreSignedUrlReq.getUserId(),
                        getPreSignedUrlReq.getNumber(),
                        getPreSignedUrlReq.getDirectory())
        );
    }

}
