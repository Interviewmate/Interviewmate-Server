package org.interviewmate.domain.question.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.behavior.model.BehaviorAnalysis;
import org.interviewmate.domain.user.model.User;

import javax.persistence.*;

@Entity
@Table(name = "question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quesId;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(columnDefinition = "TEXT")
    private String bestAnswer;
    private boolean isPortfolio;

    @Builder
    public Question(String content, String bestAnswer, boolean isPortfolio) {
        this.content = content;
        this.bestAnswer = bestAnswer;
        this.isPortfolio = isPortfolio;
    }
}
