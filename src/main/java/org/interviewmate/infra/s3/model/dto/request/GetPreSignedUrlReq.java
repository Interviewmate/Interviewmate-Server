package org.interviewmate.infra.s3.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(name = "객체 업로드 Request", description = "객체(인터뷰 동영상, 포트폴리오) 업로드에 필요한 정보")
@Getter
public class GetPreSignedUrlReq {

    @Schema(description = "유저 식별자", example = "1")
    @NotBlank(message = "유저 식별자를 입력해주세요.")
    private Long userId;

    @Schema(description = "업로드할 객체 개수", example = "10")
    @NotBlank(message = "업로드 할 파일 객체를 입력해주세요.")
    private Long number;

    @Schema(description = "객체 종류", example = "interview")
    @NotBlank(message = "객체 종류를 입력해주세요.")
    private String directory;

}