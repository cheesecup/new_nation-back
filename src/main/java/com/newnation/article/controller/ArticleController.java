package com.newnation.article.controller;

import com.newnation.article.dto.ArticleImgResponseDTO;
import com.newnation.article.dto.ArticleRequestDTO;
import com.newnation.article.dto.ArticleResponseDTO;
import com.newnation.article.service.ArticleImgService;
import com.newnation.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleImgService articleImgService;

    // 게시글 수정
    @PutMapping("/{articleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArticleResponseDTO> updateArticle(@PathVariable Long articleId, @RequestBody ArticleRequestDTO requestDTO) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(articleService.updateArticle(articleId, requestDTO));
    }

    // 게시글 삭제
    @DeleteMapping("/{articleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "게시글 삭제 성공");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 게시글 등록
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createArticle(@RequestBody ArticleRequestDTO requestDTO) {
        ArticleResponseDTO responseDTO = articleService.createArticle(requestDTO);

        return ResponseEntity.ok(responseDTO);
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
    @GetMapping("/category")
    public ResponseEntity<List<ArticleResponseDTO>> getByCategory(
            @RequestParam("category") String category
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.getByCategory(category));
    }

    // 게시글 이미지 업로드
    @PostMapping("/img")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity uploadImg(@RequestPart("img") MultipartFile img) {
        ArticleImgResponseDTO responseDTO = articleImgService.createArticleImg(img);

        return ResponseEntity.ok(responseDTO);
    }
}
