package org.interviewmate.domain.interview.repository;

import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    Boolean existsByUserAndCreatedAtBetween(User user, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Interview> findByUserAndCreatedAtBetween(User user, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
