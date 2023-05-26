package org.interviewmate.domain.interview.repository;

import java.util.List;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.InterviewVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewVideoRepository extends JpaRepository<InterviewVideo, Long> {

    List<InterviewVideo> findAllByInterview(Interview interview);
    InterviewVideo findByUrl(String url);

}
