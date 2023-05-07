package org.interviewmate.domain.portfolio.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.global.util.converter.StringArrayConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "portfolio")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {
    //model에서 키워드 추출 시 keyword 리스트를 반환하고, 질문 추천 시 해당 키워드 리스트를 그대로 전달 (질문도 동일)
    // -> 각각의 키워드 하나하나씩을 저장할 필요는 없다고 판단, column에 1:N 맵핑 해줬습니다 (converter 사용)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolio_id;

    private String url;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringArrayConverter.class)
    private List<String> keywords;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Portfolio(String url, List<String> keywords, User user) {
        this.url = url;
        this.keywords = keywords;
        this.user = user;
    }

    //portfolio 변경 시 업데이트 되어야함
    public void setUrl(String url) {
        this.url = url;
    }

    public void setKeyword(List<String> keywords) {
        this.keywords = keywords;
    }
}
