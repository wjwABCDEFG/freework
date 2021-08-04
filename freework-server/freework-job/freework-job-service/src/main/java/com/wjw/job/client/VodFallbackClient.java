package com.wjw.job.client;

import com.wjw.common.entity.Result;
import org.springframework.stereotype.Component;

/**
 * @author wjw
 * @date 2021/5/11 19:40
 */
@Component
public class VodFallbackClient implements VodClient {

    @Override
    public Result removeVideo(String videoId) {
        return Result.error().msg("timeout");
    }

    @Override
    public Result getVideoPath(String videoId) {
        return Result.error().msg("timeout");
    }


}
