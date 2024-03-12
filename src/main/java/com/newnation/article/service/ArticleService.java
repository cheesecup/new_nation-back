package com.newnation.article.service;

import com.newnation.article.dto.ArticleRequestDTO;
import com.newnation.article.dto.ArticleResponseDTO;
import com.newnation.article.entity.Article;
import com.newnation.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleImgService articleImgService;

    @Transactional
    public ArticleResponseDTO updateArticle(Long articleId, ArticleRequestDTO requestDTO) {
        // 관리자 인증 -> 보류

        // 게시글 조회
        Article article = articleExists(articleId);

        // 게시글 수정
        article.updateArticle(requestDTO);

        ArticleResponseDTO responseDTO = ArticleResponseDTO.builder()
                          .articleId(article.getArticleId())
                          .title(article.getTitle())
                          .content(article.getContent())
                          .category(article.getCategory())
                          .createdAt(article.getCreatedAt())
                          //.imgUrl(article.getImgUrl())
                          .build();
  
        return responseDTO;
    }

    public ArticleResponseDTO createArticle(ArticleRequestDTO requestDTO) throws Exception {

        Article article = articleRepository.save(new Article(requestDTO));

        // 이미지 저장
        String imgUrl = articleImgService.createArticleImg(requestDTO.getImg());

        return ArticleResponseDTO.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .content(article.getContent())
                .category(article.getCategory())
                .createdAt(article.getCreatedAt())
                .imgUrl(imgUrl)
                .build();
    }

    public void deleteArticle(Long articleId) {
        // 관리자 인증 -> 보류

        // 게시글 조회
        Article article = articleExists(articleId);

        // 게시글 삭제
        articleRepository.delete(article);

    }

    // 게시글 존재 메서드
    private Article articleExists(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
    }
}
