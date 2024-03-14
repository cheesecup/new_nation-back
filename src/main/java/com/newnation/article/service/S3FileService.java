package com.newnation.article.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.newnation.article.dto.ArticleImgResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3FileService {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    
    // S3에 이미지 업로드
    public ArticleImgResponseDTO uploadFile(MultipartFile multipartFile) {
        String oriImgName = multipartFile.getOriginalFilename(); // 원본 이미지 파일명
        String savedImgName = createFileName(oriImgName); // 저장된 이미지 파일명

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()) {
            s3Client.putObject(new PutObjectRequest(bucket, savedImgName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 업로드 실패");
        }

        String imgUrl = s3Client.getUrl(bucket, savedImgName).toString();

        return new ArticleImgResponseDTO(imgUrl, savedImgName, oriImgName);
    }
    
    // 저장된 파일 이름으로 S3 이미지 삭제
    public void deleteFile(String savedImgName) {
        s3Client.deleteObject(new DeleteObjectRequest(bucket, savedImgName));
    }
    
    // 원본 이미지 파일명 UUID를 이용하여 랜덤 형식으로 변경
    private String createFileName(String oriImgName) {
        return UUID.randomUUID().toString().concat(getFileExtension(oriImgName));
    }
    
    // 이미지 파일 확장자 자르기
    private String getFileExtension(String fileName) {
        try {
           return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
