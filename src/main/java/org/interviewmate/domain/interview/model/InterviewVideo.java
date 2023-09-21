package org.interviewmate.domain.interview.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.question.model.Question;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoId;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    Interview interview;

    @ManyToOne
    @JoinColumn(name = "question_id")
    Question question;

    private String url;

    @Builder
    public InterviewVideo(Interview interview, Question question, String url) {
        this.interview = interview;
        this.question = question;
        this.url = url;
    }

}
