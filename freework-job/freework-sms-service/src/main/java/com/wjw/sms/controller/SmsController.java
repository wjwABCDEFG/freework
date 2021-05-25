package com.wjw.sms.controller;

import com.wjw.common.entity.Result;
import com.wjw.sms.service.SmsService;
import com.wjw.sms.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wjw
 * @date 2021/5/19 3:15
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @GetMapping(value = "/send/{phone}")
    public Result code(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) return Result.ok();

        // 生成随机数
        code = RandomUtil.getFourBitRandom();

//        boolean isSend = smsService.send(phone, code);
        boolean isSend = true;
        if(isSend) {
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error().msg("发送短信失败");
        }
    }
}
