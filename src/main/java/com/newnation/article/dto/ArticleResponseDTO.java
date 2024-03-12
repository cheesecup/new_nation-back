package com.newnation.article.dto;

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
    private String imgUrl;

    public ArticleResponseDTO() {}

    @Builder
    public ArticleResponseDTO(Long articleId, String title, String content, Category category, LocalDateTime createdAt, String imgUrl) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
        this.imgUrl = imgUrl;
    }
}
