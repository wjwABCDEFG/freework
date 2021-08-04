package com.wjw.vod.controller;

import com.wjw.common.entity.Result;
import com.wjw.vod.service.VodService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wjw
 * @date 2021/5/9 21:31
 */
@RestController
@RequestMapping("/vod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("/upload")
    public Result uploadVideo(@RequestParam("file") MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return Result.ok().data(videoId);
    }

    @DeleteMapping("/remove/{videoId}")
    public Result removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                         @PathVariable String videoId){
        vodService.removeVideo(videoId);
        return Result.ok().msg("视频删除成功");
    }

    @GetMapping("/getVideoPath/{videoId}")
    public Result getVideoPath(@PathVariable String videoId){
        String path = vodService.getVideoPath(videoId);
        return Result.ok().data(path);
    }
}
