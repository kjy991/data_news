package com.collecting.collecting_data_news.domain.news;

import com.collecting.collecting_data_news.common.entity.BaseTimeEntity;
import com.collecting.collecting_data_news.domain.keyword.Keyword;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"searchNewspaper", "newspaper", "title"})})
@Entity
public class News extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Comment("뉴스 인덱스")
    private Long idx;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "keyword_idx")
    @Comment("키워드 인덱스")
    private Keyword keyword;

    @Enumerated(EnumType.STRING)
    @Comment("검색할 신문사")
    private SearchNewspaper searchNewspaper;

    @Comment("신문사")
    private String newspaper;

    @Comment("제목")
    private String title;

    @Comment("링크")
    private String href;


}