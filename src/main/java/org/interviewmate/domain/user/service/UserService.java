package org.interviewmate.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.keyword.repository.KeywordRepository;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.model.dto.request.PostUserReqDto;
import org.interviewmate.domain.user.model.dto.response.PostUserResDto;
import org.interviewmate.domain.user.repository.UserRepository;
import org.interviewmate.domain.userkeyword.model.UserKeyword;
import org.interviewmate.domain.userkeyword.repository.UserKeywordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final KeywordRepository keywordRepository;
    private final UserKeywordRepository userKeywordRepository;

    /**
     * 사용자 회원 가입
     * 1. 유저 생성 -> 2. 유저와 키워드 연결
     * @param postUserReqDto
     * @return
     */
    public PostUserResDto createUser(PostUserReqDto postUserReqDto) {

        // todo: 이메일, 닉네임 중복 검증, 패스워드 암호화 로직 추가

        // 유저 생성
        User user = PostUserReqDto.toEntity(postUserReqDto);
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
}
