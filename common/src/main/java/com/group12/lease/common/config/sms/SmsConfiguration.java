package com.group12.lease.common.config.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ChuYang
 * @version 1.0
 */
@Configuration
public class SmsConfiguration {


    @Value("${lease.sms.aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${lease.sms.aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${lease.sms.aliyun.signName}")
    private String signName;
    @Value("${lease.sms.aliyun.isPushSms}")
    private boolean isPushSms;

    private final String endPoint = "dysmsapi.aliyuncs.com";

    @Bean
    public Client smsClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint(endPoint);
        return new Client(config);
    }
}
