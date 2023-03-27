package org.interviewmate.domain.user.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

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
    // todo : 직무를 Enum을 정의하여 사용할지 고민 필요...
    private String job;

    @OneToMany(mappedBy = "user")
    private List<UserKeyword> userKeywords = new ArrayList<>();

    // todo: token 및 생성 및 수정 시간, 상태 필드 변수 추가

    @Builder
    public User(String email, String password, String nickName, String job) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.job = job;
    }
}
