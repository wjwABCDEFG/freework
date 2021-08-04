package com.wjw.job.client;

import com.wjw.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wjw
 * @date 2021/5/10 15:59
 */
@FeignClient(name = "vod-service", fallback = VodFallbackClient.class)
@Component
public interface VodClient {

    @DeleteMapping("/vod/video/remove/{videoId}")
    public Result removeVideo(@PathVariable("videoId") String videoId);

    @GetMapping("/vod/video/getVideoPath/{videoId}")
    public Result getVideoPath(@PathVariable("videoId") String videoId);
}
