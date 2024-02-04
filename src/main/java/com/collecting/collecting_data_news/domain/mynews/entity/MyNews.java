package com.collecting.collecting_data_news.domain.mynews.entity;

import com.collecting.collecting_data_news.common.entity.BaseTimeEntity;
import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.mynews.enums.SearchNewspaper;
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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"member_idx", "searchNewspaper"})})
@Entity
public class MyNews extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Comment("키워드 인덱스")
    private Long idx;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_idx")
    @Comment("유저 인덱스")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Comment("검색할 신문사")
    private SearchNewspaper searchNewspaper;

    public static MyNews toEntity(Member member, SearchNewspaper searchNewspaper) {
        return MyNews.builder()
                .member(member)
                .searchNewspaper(searchNewspaper)
                .build();
    }

    public void setMember(Member member) {
        this.member = member;
    }
}