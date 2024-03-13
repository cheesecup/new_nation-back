package com.newnation.article.service;

import com.newnation.article.dto.ArticleResponseDTO;
import com.newnation.article.entity.Article;
import com.newnation.article.entity.Category;
import com.newnation.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleImgService articleImgService;

    public List<ArticleResponseDTO> getAllArticles() {
        return articleRepository.findAll().stream().map(ArticleResponseDTO::new).toList();
    }

    public ArticleResponseDTO getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );

        return ArticleResponseDTO.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .content(article.getContent())
                .category(article.getCategory())
                .imgUrl(article.getArticleImg().getImgUrl())
                .createdAt(article.getCreatedAt())
                .build();
    }

    public List<ArticleResponseDTO> getByCategory(Category categoryEnumValue) {
        List<Article> articles = articleRepository.findByCategory(categoryEnumValue);

        return articles.stream().map(ArticleResponseDTO::new).toList();
    }
}
