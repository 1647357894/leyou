package com.leyou.upload.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.myexception.LyException;
import com.leyou.common.myexception.MyException;
import com.leyou.upload.config.UploadProperties;
import com.leyou.upload.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author WJ
 */
@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadServiceImpl implements UploadService {


    /**
     * 支持上传的文件类型
     */
   // private static final List<String> ALLOW_TYPES = Arrays.asList("image/png", "image/jpg", "image/jpeg");

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private UploadProperties prop;
    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @Override
    public String uploadImage(MultipartFile file) {
        try {
            //校验文件类型和文件内容
            String contentType = file.getContentType();
            if(!prop.getAllowTypes().contains(contentType) || ImageIO.read(file.getInputStream()) == null){
                throw new MyException(LyException.INVALID_FILE_TYPE);
            }

            //将图片上传到FASTDFS
            //获取文件后缀名
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            //上传
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);

            return prop.getBaseUrl()+storePath.getFullPath();
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new MyException(LyException.UPLOAD_FILE_ERROR);
        }
    }
}
