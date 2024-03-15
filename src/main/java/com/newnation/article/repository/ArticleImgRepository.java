package com.newnation.article.repository;

import com.newnation.article.entity.ArticleImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleImgRepository extends JpaRepository<ArticleImg, Long> {
    ArticleImg findByImgUrl(String imgUrl);

    void deleteBySavedImgName(String savedImgName);
}
