package com.group12.lease.web.admin.service.impl;

import com.group12.lease.common.exception.LeaseException;
import com.group12.lease.common.result.ResultCodeEnum;
import com.group12.lease.web.admin.service.FileService;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${lease.minio.endpointUrl}")
    private String endpoint;

    @Value("${lease.minio.bucketName}")
    private String bucketName;

    @Autowired
    private MinioClient minioClient;

    @Override
    public String fileUpload(MultipartFile file) {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).config(createBucketPolicyConfig(bucketName)).build());
            }
            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
            minioClient.putObject(PutObjectArgs.builder().
                    bucket(bucketName).
                    object(filename).
                    stream(file.getInputStream(), file.getSize(), -1).
                    contentType(file.getContentType()).build());
            return String.join("/", endpoint, bucketName, filename);
        } catch (Exception e) {
            throw new LeaseException(ResultCodeEnum.SERVICE_ERROR);
        }
    }

    /**
     * 生成用于描述指定bucket访问权限的JSON字符串。
     * 其表示，允许(`Allow`)所有人(`*`)获取(`s3:GetObject`)指定桶(`<bucket-name>`)的内容
     * @param bucketName
     * @return
     */
    private String createBucketPolicyConfig(String bucketName) {
        return """
                {
                  "Statement" : [ {
                    "Action" : "s3:GetObject",
                    "Effect" : "Allow",
                    "Principal" : "*",
                    "Resource" : "arn:aws:s3:::%s/*"
                  } ],
                  "Version" : "2012-10-17"
                }
                """.formatted(bucketName);
    }
}
