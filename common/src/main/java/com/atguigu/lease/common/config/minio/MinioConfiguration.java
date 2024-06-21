package com.atguigu.lease.common.config.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    @Value("${lease.minio.endpointUrl}")
    private String endpoint;

    @Value("${lease.minio.accessKey}")
    private String accessKey;

    @Value("${lease.minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().
                endpoint(endpoint).
                credentials(accessKey, secretKey).build();
    }
}
