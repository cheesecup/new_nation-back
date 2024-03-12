package com.newnation.article.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleImgResponseDTO {
    private String imgUrl;
    private String savedImgName;
    private String oriImgName;

    public ArticleImgResponseDTO() {}

    @Builder
    public ArticleImgResponseDTO(String imgUrl, String savedImgName, String oriImgName) {
        this.imgUrl = imgUrl;
        this.savedImgName = savedImgName;
        this.oriImgName = oriImgName;
    }
}
