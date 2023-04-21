package org.interviewmate.domain.keyword.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "키워드 관련 API")
@RestController
@RequestMapping("/keywords")
@RequiredArgsConstructor
public class KeywordApiController {


}
