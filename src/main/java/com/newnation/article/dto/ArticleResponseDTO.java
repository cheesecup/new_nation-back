package com.newnation.article.dto;

import com.newnation.article.entity.Article;
import com.newnation.article.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResponseDTO {
    private Long articleId;
    private String title;
    private String content;
    private Category category;
    private LocalDateTime createdAt;
    private String img;

    public ArticleResponseDTO() {}

    @Builder
    public ArticleResponseDTO(Long articleId, String title, String content, Category category, LocalDateTime createdAt, String imgUrl) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
        this.img = imgUrl;
    }

    public ArticleResponseDTO(Article article) {
        this.articleId = article.getArticleId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.category = article.getCategory();
        this.createdAt = article.getCreatedAt();
        this.img = article.getArticleImg().getImgUrl();
    }
}
