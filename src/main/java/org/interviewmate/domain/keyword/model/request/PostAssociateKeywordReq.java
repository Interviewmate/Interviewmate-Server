package org.interviewmate.domain.keyword.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(name = "키워드 연결 Request", description = "유저와 키워드를 연결하기 위한 정보")
@Getter
public class PostAssociateKeywordReq {

    @Schema(description = "유저 식별자")
    private Long userId;

    @Schema(description = "키워드", example = "[\"SPRING\", \"JPA\", \"JAVA\"]")
    private List<@NotBlank(message = "기술 스택, 언어를 선택하세요.") String> keywords;

}
