package org.interviewmate.domain.interview.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.behavior.model.BehaviorAnalysis;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.global.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "interview")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interId;

    //base entity에서 생성일과 수정일 자동으로 생성해줘서 뺐음
//    private LocalDate date;
//    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "behavior_analysis_id")
    private BehaviorAnalysis behaviorAnalysis;

    @Builder
    public Interview(User user, BehaviorAnalysis behaviorAnalysis) {
        this.user = user;
        this.behaviorAnalysis = behaviorAnalysis;
    }
}
