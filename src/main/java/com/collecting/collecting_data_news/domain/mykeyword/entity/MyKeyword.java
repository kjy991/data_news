package com.collecting.collecting_data_news.domain.mykeyword.entity;

import com.collecting.collecting_data_news.common.entity.BaseTimeEntity;
import com.collecting.collecting_data_news.domain.keyword.entity.Keyword;
import com.collecting.collecting_data_news.domain.member.entity.Member;
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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"member_idx", "keyword_idx"})})
@Entity
public class MyKeyword extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Comment("키워드 인덱스")
    private Long idx;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_idx")
    @Comment("유저 인덱스")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "keyword_idx")
    @Comment("키워드 인덱스")
    private Keyword keyword;

    public static MyKeyword toEntity(Member member, Keyword keyword) {
        return MyKeyword.builder()
                .member(member)
                .keyword(keyword)
                .build();
    }

    public void setMember(Member member) {
        this.member = member;
    }
}