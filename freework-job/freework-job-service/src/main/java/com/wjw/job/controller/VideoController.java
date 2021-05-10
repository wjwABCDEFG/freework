package com.wjw.job.controller;


import com.wjw.common.entity.Result;
import com.wjw.job.entity.Resume;
import com.wjw.job.entity.Video;
import com.wjw.job.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wjw
 * @since 2021-05-10
 */
@RestController
@CrossOrigin
@RequestMapping("/job/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/add")
    public Result addVideo(@RequestBody Video video){
        videoService.save(video);
        return Result.ok();
    }

    @DeleteMapping("/remove/{id}")
    public Result removeResume(@PathVariable Long id){
        videoService.removeById(id);
        return Result.ok();
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<Video> videoList = videoService.list(null);
        return Result.ok().data(videoList);
    }

}

