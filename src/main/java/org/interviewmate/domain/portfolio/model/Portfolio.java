package org.interviewmate.domain.portfolio.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.global.util.converter.StringArrayConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "portfolio")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;

    private String url;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringArrayConverter.class)
    private List<String> keywords;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Portfolio(String url, User user) {
        this.url = url;
        this.user = user;
        this.keywords = new ArrayList<>();
    }

    //portfolio 변경 시 업데이트 되어야함
    public void setUrl(String url) {
        this.url = url;
    }

    public void setKeyword(List<String> keywords) {
        this.keywords = keywords;
    }
}
