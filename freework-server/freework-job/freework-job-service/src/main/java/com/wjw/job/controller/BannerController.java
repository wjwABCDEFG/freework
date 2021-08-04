package com.wjw.job.controller;


import com.wjw.common.entity.Result;
import com.wjw.job.entity.Anonymous;
import com.wjw.job.entity.Banner;
import com.wjw.job.service.AnonymousService;
import com.wjw.job.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wjw
 * @since 2021-05-26
 */
@RestController
@RequestMapping("/job/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping("/add")
    public Result addBanner(@RequestBody Banner banner){
        bannerService.addBanner(banner);
        return Result.ok();
    }

    @DeleteMapping("/delete/{id}")
    public Result addBanner(@PathVariable String id){
        bannerService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Banner banner){
        bannerService.updateById(banner);
        return Result.ok();
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<Banner> list = bannerService.list(null);
        return Result.ok().data(list);
    }
}

