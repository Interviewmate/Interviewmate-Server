package org.interviewmate.domain.answer.repository;

import org.interviewmate.domain.answer.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
