package com.collecting.collecting_data_news.domain.member.entity;

import com.collecting.collecting_data_news.domain.member.enums.Grade;
import com.collecting.collecting_data_news.domain.member.enums.SnsType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.web.bind.annotation.GetMapping;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class Member {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_idx")
    @Comment("유저 인덱스")
    private Long idx;

    ;
    @Comment("권한")
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Comment("SNS 타입")
    @Enumerated(EnumType.STRING)
    private SnsType snsType;

    @Comment("snsId")
    private String snsId;
}