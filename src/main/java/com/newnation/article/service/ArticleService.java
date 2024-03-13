package com.newnation.article.service;

import com.newnation.article.dto.ArticleRequestDTO;
import com.newnation.article.dto.ArticleResponseDTO;
import com.newnation.article.entity.Article;
import com.newnation.article.entity.ArticleImg;
import com.newnation.article.entity.Category;
import com.newnation.article.repository.ArticleRepository;
import com.newnation.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleImgService articleImgService;

    @Transactional
    public ArticleResponseDTO updateArticle(Long articleId, ArticleRequestDTO requestDTO) throws Exception {
        // 관리자 인증 -> 보류

        // 게시글 조회
        Article article = articleExists(articleId);

        // 게시글 수정
        article.updateArticle(requestDTO);

        // 이미지 수정
        ArticleImg articleImg;
        if (requestDTO.getImg() != null) {
            articleImg = articleImgService.updateArticleImg(article.getArticleImg().getArticleImgId(), requestDTO.getImg());
        } else {
            articleImg = article.getArticleImg();
        }

        // 게시글에 수정된 이미지 설정(연관관계)
        article.setArticleImg(articleImg);

        ArticleResponseDTO responseDTO = ArticleResponseDTO.builder()
                          .articleId(article.getArticleId())
                          .title(article.getTitle())
                          .content(article.getContent())
                          .category(article.getCategory())
                          .createdAt(article.getCreatedAt())
                          .imgUrl(article.getArticleImg() != null ? article.getArticleImg().getImgUrl() : null)
                          .build();

        return responseDTO;
    }

    @Transactional
    public ArticleResponseDTO createArticle(ArticleRequestDTO requestDTO) throws Exception {
        // 이미지 저장
        ArticleImg articleImg = articleImgService.createArticleImg(requestDTO.getImg());

        Article article = articleRepository.save(new Article(requestDTO, articleImg));

        return ArticleResponseDTO.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .content(article.getContent())
                .category(article.getCategory())
                .createdAt(article.getCreatedAt())
                .imgUrl(article.getArticleImg().getImgUrl())
                .build();
    }

    @Transactional
    public void deleteArticle(Long articleId) throws Exception {
        // 관리자 인증 -> 보류

        // 게시글 조회
        Article article = articleExists(articleId);

        // 게시글 삭제
        articleRepository.delete(article);

        // 게시글 연관된 이미지 삭제
        if (article.getArticleImg() != null) {
            String imgUrl = article.getArticleImg().getImgUrl();
            articleImgService.deleteImg(imgUrl);
        }
    }

    // 게시글 존재 메서드
    private Article articleExists(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() ->
                new NotFoundException("해당 게시글을 찾을 수 없습니다."));
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<ArticleResponseDTO> getAllArticles() {
        return articleRepository.findAll().stream().map(ArticleResponseDTO::new).toList();
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public ArticleResponseDTO getArticle(Long articleId) {
        Article article = articleExists(articleId);

        return ArticleResponseDTO.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .content(article.getContent())
                .category(article.getCategory())
                .imgUrl(article.getArticleImg().getImgUrl())
                .createdAt(article.getCreatedAt())
                .build();
    }

    // 게시글 카테고리별 조회
    @Transactional(readOnly = true)
    public List<ArticleResponseDTO> getByCategory(String category) {
        List<Article> articles = new ArrayList<>();
        if (Category.contains(category)) {
            articles = articleRepository.findByCategory(Category.valueOf(category));
        } else {
            articles = articleRepository.findAll();
        }
    }
}
