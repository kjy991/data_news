package com.collecting.collecting_data_news.domain.keyword.entity;

import com.collecting.collecting_data_news.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Keyword extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Comment("키워드 인덱스")
    private Long idx;

    @Comment("키워드")
    private String word;

    public static Keyword toEntity(String keyword) {
        return Keyword.builder().word(keyword).build();
    }
}