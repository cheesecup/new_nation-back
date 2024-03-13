package com.newnation.article.controller;

import com.newnation.article.dto.ArticleRequestDTO;
import com.newnation.article.dto.ArticleResponseDTO;
import com.newnation.article.entity.Category;
import com.newnation.article.service.ArticleService;
import com.newnation.global.response.SuccessResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URL;
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
    public ResponseEntity<ArticleResponseDTO> updateArticle(@PathVariable Long articleId, @ModelAttribute ArticleRequestDTO requestDTO) throws Exception {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(articleService.updateArticle(articleId, requestDTO));
    }

    // 게시글 삭제
    @DeleteMapping("/{articleId}")
    public ResponseEntity<Map<String, String>> deleteArticle(@PathVariable Long articleId) throws Exception {
        articleService.deleteArticle(articleId);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "게시글 삭제 성공");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 게시글 등록
    @PostMapping
    public ResponseEntity createArticle(@ModelAttribute ArticleRequestDTO requestDTO) throws Exception {
            ArticleResponseDTO responseDTO = articleService.createArticle(requestDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.getArticleId())
                .toUri();
            return ResponseEntity.created(location).body(responseDTO);
    }

//    @PostMapping
//    public ResponseEntity createArticle(@ModelAttribute ArticleRequestDTO requestDTO) {
//        try {
//            ArticleResponseDTO responseDTO = articleService.createArticle(requestDTO);
//
//            return ResponseEntity.ok(responseDTO);
//        } catch (Exception e) {
//            return ResponseEntity.status(400).body(e.getMessage());
//        }
//    }


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
