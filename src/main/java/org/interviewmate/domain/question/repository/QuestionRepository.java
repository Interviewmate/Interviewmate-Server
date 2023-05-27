package org.interviewmate.domain.question.repository;

import org.interviewmate.domain.question.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
