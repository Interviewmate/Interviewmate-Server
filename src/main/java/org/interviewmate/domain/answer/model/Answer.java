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
    @Column(columnDefinition = "TEXT")
    private String answerAnalysis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inter_id")
    private Interview interview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ques_id")
    private Question question;

    @Builder
    public Answer(String content, Interview interview, Question question, String answerAnalysis) {
        this.content = content;
        this.interview = interview;
        this.question = question;
        this.answerAnalysis = answerAnalysis;
    }
}
