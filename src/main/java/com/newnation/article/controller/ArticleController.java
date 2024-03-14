package com.newnation.article.controller;

import com.newnation.article.dto.ArticleImgResponseDTO;
import com.newnation.article.dto.ArticleRequestDTO;
import com.newnation.article.dto.ArticleResponseDTO;
import com.newnation.article.service.ArticleService;
import com.newnation.article.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;
    private final S3FileService s3FileService;

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

    // 게시글 등록
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createArticle(@ModelAttribute ArticleRequestDTO requestDTO) throws Exception {
            ArticleResponseDTO responseDTO = articleService.createArticle(requestDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.getArticleId())
                .toUri();
            return ResponseEntity.created(location).body(responseDTO);
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
    public ResponseEntity uploadImg(@RequestPart("img") MultipartFile img) {
        ArticleImgResponseDTO responseDTO = s3FileService.uploadFile(img);

        return ResponseEntity.ok(responseDTO);
    }
}
