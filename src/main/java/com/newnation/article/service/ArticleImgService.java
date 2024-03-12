package com.newnation.article.service;

import com.newnation.article.repository.ArticleImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleImgService {

    private final ArticleImgRepository articleImgRepository;
}
