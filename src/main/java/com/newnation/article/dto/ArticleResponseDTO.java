package com.newnation.article.dto;

import com.newnation.article.entity.Article;
import com.newnation.article.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ArticleResponseDTO {
    private Long articleId;
    private String title;
    private String content;
    private Category category;
    private String createdAt;
    private String imgUrl;

    public ArticleResponseDTO() {}

    @Builder
    public ArticleResponseDTO(Long articleId, String title, String content, Category category, LocalDateTime createdAt, String imgUrl) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.imgUrl = imgUrl;
    }

    public ArticleResponseDTO(Article article) {
        this.articleId = article.getArticleId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.category = article.getCategory();
        this.createdAt = article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.imgUrl = article.getArticleImg().getImgUrl();
    }
}
