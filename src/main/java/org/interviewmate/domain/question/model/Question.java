package org.interviewmate.domain.question.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.global.util.converter.StringArrayConverter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    private String keyword;
    @Column(columnDefinition = "TEXT")
    private String question;
    @Column(columnDefinition = "TEXT")
    private String bestAnswer;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringArrayConverter.class)
    private List<String> questionToken;

    @Builder
    public Question(String keyword, String question, String bestAnswer, List<String> questionToken) {
        this.keyword = keyword;
        this.question = question;
        this.bestAnswer = bestAnswer;
        this.questionToken = questionToken;
    }
}
