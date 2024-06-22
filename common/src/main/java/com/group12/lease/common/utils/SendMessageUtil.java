package com.group12.lease.common.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.group12.lease.common.exception.LeaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.group12.lease.common.result.ResultCodeEnum.SERVICE_ERROR;

/**
 * @author ChuYang
 * @version 1.0
 */
@Component
public class SendMessageUtil {

    @Autowired
    private Client client;

    @Value("${lease.sms.aliyun.signName}")
    private String signName;

    @Value("${lease.sms.aliyun.templateCode}")
    private String templateCode;

    public SendSmsResponse sendSms2phone(String phone, String verificationCode) {
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setTemplateParam(verificationCode);
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = client.sendSms(sendSmsRequest);
        } catch (Exception e) {
            throw new LeaseException(SERVICE_ERROR);
        }
        return sendSmsResponse;
    }
}
