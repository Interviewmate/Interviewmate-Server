package org.interviewmate.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.keyword.repository.KeywordRepository;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.model.dto.request.PostUserReq;
import org.interviewmate.domain.user.model.dto.response.PostUserRes;
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
     * @param postUserReq
     * @return
     */
    public PostUserRes createUser(PostUserReq postUserReq) {

        // 유저 생성
        User user = PostUserReq.toEntity(postUserReq);
        userRepository.save(user);

        // 유저-키워드 연결
        List<UserKeyword> userKeywords = postUserReq.getKeyword().stream()
                .map(keyword -> UserKeyword.builder()
                        .user(user)
                        .keyword( keywordRepository.findByName(keyword))
                        .build())
                .collect(Collectors.toList());
        userKeywordRepository.saveAll(userKeywords);

        return PostUserRes.of(user);

    }
}
