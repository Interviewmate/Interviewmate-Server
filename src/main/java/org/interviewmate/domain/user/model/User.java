package org.interviewmate.domain.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    // todo : 직무를 Enum을 정의하여 사용할지 고민 필요...
    private String job;

    // todo: token 및 생성 및 수정 시간, 상태 필드 변수 추가

    @Builder
    public User(String email, String password, String nickName, String job) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.job = job;
    }
}
