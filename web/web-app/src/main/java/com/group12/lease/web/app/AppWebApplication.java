package com.group12.lease.web.app;


import com.group12.lease.common.config.minio.MinioConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.group12.lease.web.app.mapper")
@SpringBootApplication(exclude = MinioConfiguration.class)
public class AppWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppWebApplication.class, args);
    }
}
