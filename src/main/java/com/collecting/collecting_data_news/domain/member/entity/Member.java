package com.collecting.collecting_data_news.domain.member.entity;

import com.collecting.collecting_data_news.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"oauthType", "oauthId"})})
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Comment("유저 인덱스")
    private Long idx;

    @Comment("oAuth 타입")
    private String oauthType;

    @Comment("oAuthId")
    private String oauthId;


    @Comment("이름")
    private String nickname;

    @Comment("비밀번호")
    private String provider;

    @Comment("이메일")
    private String email;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    List<MyKeyword> myKeywords = new ArrayList<>();

    public void addMyKeywords(MyKeyword myKeyword) {
        myKeyword.setMember(this);
        this.myKeywords.add(myKeyword);
    }
}