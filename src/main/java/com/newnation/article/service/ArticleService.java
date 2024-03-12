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
}
