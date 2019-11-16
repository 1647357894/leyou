package com.leyou.upload.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    /**
     * 文件上传
     * @param file
     * @return
     */
    String uploadImage(MultipartFile file);
}
