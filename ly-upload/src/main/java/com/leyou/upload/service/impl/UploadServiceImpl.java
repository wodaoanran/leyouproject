package com.leyou.upload.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.upload.controller.UploadController;
import com.leyou.upload.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {


    private static final Logger logger= LoggerFactory.getLogger(UploadController.class);
    //支持的文件类型 （images）
    private static final List<String> suffixes= Arrays.asList("image/png", "image/jpeg","image/jpg");

    @Autowired
    private FastFileStorageClient storageClient;

    @Override
    public String upload(MultipartFile file) {

        try {

            String type = file.getContentType();
            if (!suffixes.contains(type)) {
                logger.info("上传文件失败，文件类型不匹配", type);
                return null;

            }
            BufferedImage image = ImageIO.read(file.getInputStream());

            if (image == null) {
                logger.info("上传文件失败，文件内容不符合要求");
                return null;
            }
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);

            String url = "http://image.leyou.com/" + storePath.getFullPath();
            return url;
        }catch(Exception e){
            return null;
        }

    }
}
