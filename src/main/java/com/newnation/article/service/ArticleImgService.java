package com.newnation.article.service;

import com.newnation.article.dto.ArticleImgResponseDTO;
import com.newnation.article.entity.ArticleImg;
import com.newnation.article.repository.ArticleImgRepository;
import com.newnation.global.exception.ImgSaveFailed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleImgService {

    private final ArticleImgRepository articleImgRepository;
    private final S3FileService s3FileService;

    @Value("${imgLocation}")
    private String imgLocation;

    @Transactional
    public ArticleImg createArticleImg(MultipartFile img) {
        // 이미지 업로드
        ArticleImgResponseDTO articleImgResponseDTO = s3FileService.uploadFile(img);

        return articleImgRepository.save(new ArticleImg(articleImgResponseDTO.getImgUrl(),
                articleImgResponseDTO.getSavedImgName(),
                articleImgResponseDTO.getOriImgName()));
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

    // 이미지 파일 저장
    public ArticleImgResponseDTO upload(MultipartFile img) {
        if (img.isEmpty()) {
            throw new ImgSaveFailed("이미지 파일이 없습니다.");
        }

        String oriImgName = img.getOriginalFilename();
        String savedImgName = "";
        String imgUrl = "";

        // 이미지 저장 폴더가 없을시 폴더 생성
        File folder = new File(imgLocation);
        if (!folder.exists()) {
            folder.mkdir();
        }

        if (oriImgName != null && !oriImgName.isEmpty()) {

            UUID uuid = UUID.randomUUID();
            String extension = oriImgName.substring(oriImgName.lastIndexOf(".")); //확장자 자르기 추출

            savedImgName = uuid.toString() + extension;
            String uploadImgFullPath = imgLocation + "/" + savedImgName;

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(uploadImgFullPath);
                fileOutputStream.write(img.getBytes());
                fileOutputStream.close();
            } catch (IOException e) {
                throw new ImgSaveFailed("이미지 파일 저장 실패");
            }

            imgUrl = imgLocation + "/" + savedImgName;
        }

        return ArticleImgResponseDTO.builder()
                .imgUrl(imgUrl)
                .savedImgName(savedImgName)
                .oriImgName(oriImgName)
                .build();
    }

    // 이미지 파일 삭제
    public void deleteImg(String imgUrl) {
        File img = new File(imgUrl);

        if (img.exists()) {
            img.delete();
        }
    }
}
