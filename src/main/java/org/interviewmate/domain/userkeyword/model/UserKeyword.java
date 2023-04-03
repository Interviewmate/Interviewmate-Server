package org.interviewmate.domain.userkeyword.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.keyword.model.Keyword;
import org.interviewmate.domain.user.model.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userKeywordId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @Builder
    public UserKeyword(User user, Keyword keyword) {
        this.user = user;
        this.keyword = keyword;
    }

}
