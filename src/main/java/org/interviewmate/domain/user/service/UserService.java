package org.interviewmate.domain.user.service;

import static org.interviewmate.global.error.ErrorCode.DUPLICATE_NICKNAME;
import static org.interviewmate.global.error.ErrorCode.FAIL_TO_LOGIN;
import static org.interviewmate.global.util.encrypt.Secret.PASSWORD_KEY;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.auth.model.dto.request.LoginReq;
import org.interviewmate.domain.keyword.repository.KeywordRepository;
import org.interviewmate.domain.user.exception.UserException;
import org.interviewmate.domain.user.model.Authority;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.model.dto.request.PostUserReqDto;
import org.interviewmate.domain.user.model.dto.response.PostUserResDto;
import org.interviewmate.domain.user.repository.UserRepository;
import org.interviewmate.domain.userkeyword.model.UserKeyword;
import org.interviewmate.domain.userkeyword.repository.UserKeywordRepository;
import org.interviewmate.global.util.encrypt.password.AES128;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final KeywordRepository keywordRepository;
    private final UserKeywordRepository userKeywordRepository;

    /**
     * 사용자 회원 가입
     * 1. 유저 생성 -> 2. 유저와 키워드 연결
     */
    public PostUserResDto createUser(PostUserReqDto postUserReqDto) {

        // 유저 생성
        User user = PostUserReqDto.toEntity(postUserReqDto);
        user.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));
        userRepository.save(user);

        // 유저-키워드 연결
        List<UserKeyword> userKeywords = postUserReqDto.getKeywords().stream()
                .map(keyword -> UserKeyword.builder()
                        .user(user)
                        .keyword( keywordRepository.findByName(keyword))
                        .build())
                .collect(Collectors.toList());
        userKeywordRepository.saveAll(userKeywords);

        return PostUserResDto.of(user);

    }

    /**
     * 사용자 이메일, 패스워드 검증
     */
    public User login(LoginReq loginReq) {

        // 이메일로 User 찾기
        User user = userRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(() -> new UserException(FAIL_TO_LOGIN));

        String password = new AES128(PASSWORD_KEY).decrypt(user.getPassword());

        // 비밀번호 일치 확인
        if (!Objects.equals(password, loginReq.getPassword())) {
            throw new UserException(FAIL_TO_LOGIN);
        }

        return user;

    }

    /**
     *  닉네임 검증
     */
    public String checkNickname(String nickName) {

        User user = userRepository.findByNickName(nickName).orElse(null);

        if(!Objects.isNull(user)) {
            throw new UserException(DUPLICATE_NICKNAME);
        }

        return "생성 가능한 닉네임입니다.";
    }
}
