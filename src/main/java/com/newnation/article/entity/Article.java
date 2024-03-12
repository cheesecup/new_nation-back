package com.newnation.article.entity;

import com.newnation.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Article extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    protected Article() {}
}
