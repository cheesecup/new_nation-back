package com.newnation.article.repository;

import com.newnation.article.entity.Article;
import com.newnation.article.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByCategoryOrderByArticleIdDesc(Category category);

    List<Article> findAllByOrderByArticleIdDesc();
}
