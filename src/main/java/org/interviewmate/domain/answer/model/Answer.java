package org.interviewmate.domain.answer.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.question.model.Question;

import javax.persistence.*;

@Entity
@Table(name = "answer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ansId;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "inter_id")
    private Interview interview;

    @ManyToOne
    @JoinColumn(name = "ques_id")
    private Question question;

    @Builder
    public Answer(String content, Interview interview, Question question) {
        this.content = content;
        this.interview = interview;
        this.question = question;
    }
}
