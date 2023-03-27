package org.interviewmate.debug;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;
import org.interviewmate.global.util.response.ResponseCode;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.interviewmate.global.util.response.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@Tag(name = "테스트용 컨트롤러", description = "개발하면서 필요한 debug용 Controller")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/success")
    public ResponseDto successRepsonseTest() {
        ArrayList<String> list = new ArrayList<>();
        list.add("성공1");
        list.add("성공2");
        return ResponseUtil.SUCCESS(ResponseCode.SUCCESS, list);
    }

    @GetMapping("/failure")
    public ResponseDto failResponseTest() {
        return ResponseUtil.FAILURE(ResponseCode.FAILURE_ALREADY_REPORTED, new ArrayList<>());
    }

    @GetMapping("/error")
    public void errorResponseTest() {
        throw new CustomException(ErrorCode.NOT_FOUND_DATA);
    }

    @GetMapping("/exception")
    public void exceptionResponseTest() {
        throw new TestException(ErrorCode.NOT_FOUND_DATA);
    }
}
