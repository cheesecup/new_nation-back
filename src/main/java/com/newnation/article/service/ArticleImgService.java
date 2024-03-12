package com.newnation.article.service;

import com.newnation.article.entity.ArticleImg;
import com.newnation.article.repository.ArticleImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ArticleImgService {

    private final ArticleImgRepository articleImgRepository;

    @Value("${imgLocation}")
    private String imgLocation;

    public String createArticleImg(MultipartFile img) throws Exception {
        String oriImgName = img.getOriginalFilename(); //이미지 원본 이름
        String savedImgName = "";
        String imgUrl = "";

        if (!oriImgName.isEmpty()) {
            savedImgName = upload(oriImgName, img.getBytes());
            imgUrl = imgLocation + "/" + savedImgName;
        }

        articleImgRepository.save(new ArticleImg(imgUrl, savedImgName, oriImgName));

        return imgUrl;
    }

    private String upload(String oriImgName, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID();
        String extension = oriImgName.substring(oriImgName.lastIndexOf(".")); //확장자 자르기 추출

        String savedImgName = uuid.toString() + extension;
        String uploadImgFullPath = imgLocation + "/" + savedImgName;

        FileOutputStream fileOutputStream = new FileOutputStream(uploadImgFullPath);
        fileOutputStream.write(fileData);
        fileOutputStream.close();

        return savedImgName;
    }
}
