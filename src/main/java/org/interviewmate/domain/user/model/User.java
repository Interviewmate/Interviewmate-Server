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
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Column(unique = true)
    private String nickName;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Job job;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<UserKeyword> userKeywords = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private BaseStatus baseStatus;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Authority> roles = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

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

    public void setRoles(List<Authority> roles) {
        this.roles = roles;
        roles.forEach(role -> role.setUser(this));
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

