package org.interviewmate.domain.keyword.service;

import static org.interviewmate.global.error.ErrorCode.EMPTY_KEYWORD;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.keyword.exception.KeywordException;
import org.interviewmate.domain.keyword.repository.KeywordRepository;
import org.interviewmate.domain.user.exception.UserException;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.model.dto.request.PostAssociateKeywordReq;
import org.interviewmate.domain.user.repository.UserRepository;
import org.interviewmate.domain.userkeyword.model.UserKeyword;
import org.interviewmate.domain.userkeyword.repository.UserKeywordRepository;
import org.interviewmate.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final UserRepository userRepository;
    private final UserKeywordRepository userKeywordRepository;

    public String associateUserWithKeyword(PostAssociateKeywordReq postAssociateKeywordReq) {

        if (postAssociateKeywordReq.getKeywords().isEmpty()) {
            throw new KeywordException(EMPTY_KEYWORD);
        }

        User user = userRepository.findById(postAssociateKeywordReq.getUserId())
                .orElseThrow(() -> new UserException(ErrorCode.NOT_EXIST_USER));

        List<UserKeyword> userKeywords = postAssociateKeywordReq.getKeywords().stream()
                .map(keyword -> UserKeyword.builder()
                        .user(user)
                        .keyword(keywordRepository.findByName(keyword).orElseThrow(() -> new KeywordException(ErrorCode.INVALID_KEYWORD)))
                        .build())
                .collect(Collectors.toList());
        userKeywordRepository.saveAll(userKeywords);

        return "유저와 키워드 연결 완료.";
    }

}
