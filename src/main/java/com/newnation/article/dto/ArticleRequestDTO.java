package com.newnation.article.dto;

import com.newnation.article.entity.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ArticleRequestDTO {

    private String title;
    private String content;
    private Category category;
    private String imgUrl; // 수정할 부분
}
