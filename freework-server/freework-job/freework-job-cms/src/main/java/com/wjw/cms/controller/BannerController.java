package com.wjw.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjw.cms.entity.Banner;
import com.wjw.cms.service.BannerService;
import com.wjw.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wjw
 * @since 2021-05-16
 */
@RestController
@RequestMapping("/cms/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping("/add")
    public Result addBanner(@RequestBody Banner banner){
        bannerService.addBanner(banner);
        return Result.ok();
    }

    @DeleteMapping("/remove/{id}")
    public Result removeBanner(@PathVariable Long id){
        bannerService.removeBanner(id);
        return Result.ok();
    }

    @PostMapping("/removeBatch")
    public Result removeBatch(@RequestBody List<Long> rids){
        bannerService.removeByIds(rids);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result updateBanner(@RequestBody Banner banner){
        bannerService.updateBanner(banner);
        return Result.ok();
    }

    @GetMapping("/findPage/{pageNum}/{pageSize}")
    public Result pageBanner(@PathVariable long pageNum,
                              @PathVariable long pageSize){
        Page<Banner> pageInfo = new Page<>(pageNum, pageSize);
        bannerService.page(pageInfo, null);
        return Result.ok().data(pageInfo);
    }

    @GetMapping("/findById/{id}")
    public Result findBannerById(@PathVariable Long id){
        Banner company = bannerService.getById(id);
        return Result.ok().data(company);
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<Banner> banners = bannerService.findAll();
        return Result.ok().data(banners);
    }

    @PostMapping("/updateSort/{id}/{direct}")
    public Result updateSort(@PathVariable Long id,
                             @PathVariable int direct){
        bannerService.updateSort(id, direct);
        return Result.ok();
    }
}

