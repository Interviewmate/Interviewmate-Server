package org.interviewmate.domain.user.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.interviewmate.domain.user.model.User;

@Schema(name = "회원 정보 Response", description = "회원에 대한 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class FindUserInformationOutDto {

    private String nickname;
    private String job;

    public static FindUserInformationOutDto of(User user) {
      return FindUserInformationOutDto.builder()
              .nickname(user.getNickName())
              .job(user.getJob().toString())
              .build();
    }
}
