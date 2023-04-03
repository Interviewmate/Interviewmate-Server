package org.interviewmate.domain.user.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.userkeyword.model.UserKeyword;
import org.interviewmate.global.common.BaseEntity;
import org.interviewmate.global.common.BaseStatus;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String nickName;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Job job;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<UserKeyword> userKeywords = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private BaseStatus baseStatus;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    @Column(columnDefinition = "TEXT")
    private String accessToken;

    @Builder
    public User(String email, String password, String nickName, Job job) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.job = job;
        this.baseStatus = BaseStatus.ACTIVE;
    }

    public void setBaseStatus(BaseStatus baseStatus) {
        this.baseStatus = baseStatus;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
