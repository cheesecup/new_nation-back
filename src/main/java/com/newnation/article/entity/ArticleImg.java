package com.newnation.article.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ArticleImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleImgId;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String savedImgName;

    @Column(nullable = false)
    private String oriImgName;

    @OneToOne(mappedBy = "articleImg")
    private Article article;
}
