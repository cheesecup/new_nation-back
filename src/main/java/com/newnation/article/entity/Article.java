package com.newnation.article.entity;

import com.newnation.article.dto.ArticleRequestDTO;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "article_img_id")
    private ArticleImg articleImg;

    protected Article() {}

    public void setArticleImg(ArticleImg articleImg) {
        this.articleImg = articleImg;
    }

    public void updateArticle(ArticleRequestDTO requestDTO) {
        this.title = requestDTO.getTitle();
        this.content = requestDTO.getContent();
        this.category = requestDTO.getCategory();
    }

    public Article(ArticleRequestDTO requestDTO, ArticleImg articleImg) {
        this.title = requestDTO.getTitle();
        this.content = requestDTO.getContent();
        this.category = requestDTO.getCategory();
        this.articleImg = articleImg;
    }
}
