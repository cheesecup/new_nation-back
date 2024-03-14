package com.newnation.article.service;

import com.newnation.article.dto.ArticleImgResponseDTO;
import com.newnation.article.entity.ArticleImg;
import com.newnation.article.repository.ArticleImgRepository;
import com.newnation.global.exception.ImgSaveFailed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleImgService {

    private final ArticleImgRepository articleImgRepository;
    private final S3FileService s3FileService;

    @Transactional
    public ArticleImgResponseDTO createArticleImg(MultipartFile img) {
        // 이미지 업로드
        ArticleImgResponseDTO articleImgResponseDTO = s3FileService.uploadFile(img);

        ArticleImg articleImg = articleImgRepository.save(new ArticleImg(articleImgResponseDTO.getImgUrl(),
                articleImgResponseDTO.getSavedImgName(),
                articleImgResponseDTO.getOriImgName()));

        return articleImgResponseDTO;
    }

    @Transactional
    public ArticleImg updateArticleImg(Long articleImgId, MultipartFile img) {
        ArticleImg articleImg = articleImgRepository.findById(articleImgId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이미지를 찾을 수 없습니다."));

        // 기존 이미지 삭제
        s3FileService.deleteFile(articleImg.getSavedImgName());

        // 수정 이미지 업로드
        ArticleImgResponseDTO articleImgResponseDTO = s3FileService.uploadFile(img);

        // 게시글 이미지 정보 수정
        articleImg.updateArticleImg(articleImgResponseDTO.getImgUrl(),
                articleImgResponseDTO.getSavedImgName(),
                articleImgResponseDTO.getOriImgName());

        return articleImg;
    }
}
