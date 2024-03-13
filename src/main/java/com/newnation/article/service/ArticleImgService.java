package com.newnation.article.service;

import com.newnation.article.dto.ArticleImgResponseDTO;
import com.newnation.article.entity.ArticleImg;
import com.newnation.article.repository.ArticleImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ArticleImgService {

    private final ArticleImgRepository articleImgRepository;

    @Value("${imgLocation}")
    private String imgLocation;

    @Transactional
    public ArticleImg createArticleImg(MultipartFile img) throws Exception {
        // 이미지 업로드
        ArticleImgResponseDTO articleImgResponseDTO = upload(img);

        return articleImgRepository.save(new ArticleImg(articleImgResponseDTO.getImgUrl(),
                articleImgResponseDTO.getSavedImgName(),
                articleImgResponseDTO.getOriImgName()));
    }

    @Transactional
    public ArticleImg updateArticleImg(Long articleImgId, MultipartFile img) throws Exception {
        ArticleImg articleImg = articleImgRepository.findById(articleImgId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이미지를 찾을 수 없습니다."));

        // 기존 이미지 삭제
        deleteImg(articleImg.getImgUrl());

        // 수정 이미지 업로드
        ArticleImgResponseDTO articleImgResponseDTO = upload(img);
        
        // 게시글 이미지 정보 수정
        articleImg.updateArticleImg(articleImgResponseDTO.getImgUrl(),
                articleImgResponseDTO.getSavedImgName(),
                articleImgResponseDTO.getOriImgName());

        return articleImg;
    }

    public ArticleImgResponseDTO upload(MultipartFile img) throws Exception {
        String oriImgName = img.getOriginalFilename(); //이미지 원본 이름
        String savedImgName = "";
        String imgUrl = "";
        if (!oriImgName.isEmpty()) {

            UUID uuid = UUID.randomUUID();
            String extension = oriImgName.substring(oriImgName.lastIndexOf(".")); //확장자 자르기 추출

            savedImgName = uuid.toString() + extension;
            String uploadImgFullPath = imgLocation + "/" + savedImgName;

            FileOutputStream fileOutputStream = new FileOutputStream(uploadImgFullPath);
            fileOutputStream.write(img.getBytes());
            fileOutputStream.close();

            imgUrl = imgLocation + "/" + savedImgName;
        }

        return ArticleImgResponseDTO.builder()
                .imgUrl(imgUrl)
                .savedImgName(savedImgName)
                .oriImgName(oriImgName)
                .build();
    }

    public void deleteImg(String imgUrl) throws Exception {
        File img = new File(imgUrl);

        if (img.exists()) {
            img.delete();
        }
    }
}
