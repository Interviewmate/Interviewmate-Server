package org.interviewmate.infra.s3.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "이미지 업로드 Response : 미리 서명된 주소를 받아 이미지 업로드")
@Getter
@Builder(access = AccessLevel.PROTECTED)
public class GetPreSignedUrlRes {

    @Schema(description = "업로드 URL")
    List<String> preSignedUrl;

    public static GetPreSignedUrlRes of(List<String> preSignedUrls) {
        return GetPreSignedUrlRes.builder()
                .preSignedUrl(preSignedUrls)
                .build();
    }


}