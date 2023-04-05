package org.interviewmate.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.answer.repository.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
}
