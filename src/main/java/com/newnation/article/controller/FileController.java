package com.newnation.article.controller;

import com.newnation.global.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FileController {

    private final S3Uploader s3Uploader;

    // 게시글 이미지 업로드
    @PostMapping("/img")
    public ResponseEntity uploadImg(@RequestPart("img") MultipartFile img) {
        String imgUrl = s3Uploader.uploadFile(img);

        Map<String, String> response = new HashMap<>();
        response.put("imgUrl", imgUrl);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/img")
    public ResponseEntity deleteImg(@RequestParam String fileName) {
        s3Uploader.deleteFile(fileName);

        Map<String, String> response = new HashMap<>();
        response.put("imgUrl", fileName);
        return ResponseEntity.ok(response);
    }
}
