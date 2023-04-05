package org.interviewmate.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
}
