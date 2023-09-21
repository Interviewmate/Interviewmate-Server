package org.interviewmate.domain.answer.repository;

import org.interviewmate.domain.answer.model.Answer;
import org.interviewmate.domain.interview.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByInterview(Interview interview);
}
