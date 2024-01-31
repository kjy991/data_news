package com.collecting.collecting_data_news.domain;


import jakarta.persistence.*;

@Entity
public class News {
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
