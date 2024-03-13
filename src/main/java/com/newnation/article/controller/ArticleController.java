package com.newnation.article.controller;

import com.newnation.article.dto.ArticleResponseDTO;
import com.newnation.article.entity.Category;
import com.newnation.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("")
    public ResponseEntity<List<ArticleResponseDTO>> getAllArticles() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.getAllArticles());
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDTO> getArticle(
            @PathVariable Long articleId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.getArticle(articleId));
    }

    @GetMapping("/category/{categoryEnumValue}")
    public ResponseEntity<List<ArticleResponseDTO>> getByCategory(
            @PathVariable Category categoryEnumValue
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.getByCategory(categoryEnumValue));
    }
}
