package com.wjw.sms.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjw
 * @date 2021/5/19 3:16
 */
@Service
//@EnableConfigurationProperties(SmsProperties.class)
@Transactional
public class SmsService {

//    @Autowired
//    private SmsProperties prop;

    @Value("${freework.sms.accessKeyId}")
    private String accessKeyId;
    @Value("${freework.sms.accessKeySecret}")
    private String accessKeySecret;
    @Value("${freework.sms.signName}")
    private String signName;
    @Value("${freework.sms.verifyCodeTemplate}")
    private String verifyCodeTemplate;

    static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    public boolean send(String phone, String code) {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", verifyCodeTemplate);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        request.putQueryParameter("OutId", "suibian123");

        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);

            logger.info("发送短信状态：{}", response.getHttpStatus());
            logger.info("发送短信消息：{}", response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
