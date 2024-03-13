package com.newnation.article.controller;

import com.newnation.article.dto.ArticleRequestDTO;
import com.newnation.article.dto.ArticleResponseDTO;
import com.newnation.article.entity.Category;
import com.newnation.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;


    // 게시글 수정
    @PutMapping("/{articleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArticleResponseDTO> updateArticle(@PathVariable Long articleId, @ModelAttribute ArticleRequestDTO requestDTO) throws Exception {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(articleService.updateArticle(articleId, requestDTO));
    }

    // 게시글 삭제
    @DeleteMapping("/{articleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteArticle(@PathVariable Long articleId) throws Exception {
        articleService.deleteArticle(articleId);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "게시글 삭제 성공");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 게시글 작성
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createArticle(@ModelAttribute ArticleRequestDTO requestDTO) {
        try {
            ArticleResponseDTO responseDTO = articleService.createArticle(requestDTO);

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<ArticleResponseDTO>> getAllArticles() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.getAllArticles());
    }

    // 게시글 상세 조회
    @GetMapping("/{articleId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ArticleResponseDTO> getArticle(
            @PathVariable Long articleId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.getArticle(articleId));
    }

    // 게시글 카테고리별 조회
    @GetMapping("/category/{categoryEnumValue}")
    public ResponseEntity<List<ArticleResponseDTO>> getByCategory(
            @PathVariable Category categoryEnumValue
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.getByCategory(categoryEnumValue));
    }
}
