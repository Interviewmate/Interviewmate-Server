package org.interviewmate.domain.interview.repository;

import org.interviewmate.domain.interview.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByUserIdAndCreatedAtBetween(LocalDate firstDate, LocalDate lastDate);
}
