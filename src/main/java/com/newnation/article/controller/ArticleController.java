package com.newnation.article.controller;

import com.newnation.article.dto.ArticleRequestDTO;
import com.newnation.article.dto.ArticleResponseDTO;
import com.newnation.article.service.ArticleService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;


    // 게시글 수정
    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDTO> updateArticle(@PathVariable Long articleId, @RequestBody ArticleRequestDTO requestDTO) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(articleService.updateArticle(articleId, requestDTO));
    }

    // 게시글 삭제
    @DeleteMapping("/{articleId}")
    public ResponseEntity<Map<String, String>> deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "게시글 삭제 성공");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping
    public ResponseEntity createArticle(@ModelAttribute ArticleRequestDTO requestDTO) {
        try {
            ArticleResponseDTO responseDTO = articleService.createArticle(requestDTO);

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
