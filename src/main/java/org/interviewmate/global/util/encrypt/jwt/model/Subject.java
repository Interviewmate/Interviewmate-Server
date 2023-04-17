package org.interviewmate.global.util.encrypt.jwt.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Subject {

    private String email;
    private TokenType type;

    @Builder
    public Subject(String email, TokenType type) {
        this.email = email;
        this.type = type;
    }


}
