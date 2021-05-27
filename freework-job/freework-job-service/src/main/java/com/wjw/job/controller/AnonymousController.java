package com.wjw.job.controller;


import com.wjw.common.entity.Result;
import com.wjw.job.entity.Anonymous;
import com.wjw.job.entity.Company;
import com.wjw.job.entity.vo.AnoQuery;
import com.wjw.job.entity.vo.CompanyQuery;
import com.wjw.job.service.AnonymousService;
import com.wjw.job.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wjw
 * @since 2021-05-26
 */
@RestController
@RequestMapping("/job/anonymous")
public class AnonymousController {

    @Autowired
    private AnonymousService anonymousService;

    @PostMapping("/add")
    public Result addAnonymous(@RequestBody Anonymous anonymous){
        anonymousService.save(anonymous);
        return Result.ok();
    }

    @PostMapping("/star/{id}")
    public Result star(@PathVariable String id){
        Anonymous anonymous = anonymousService.getById(id);
        anonymous.setStar(anonymous.getStar() + 1);
        anonymousService.updateById(anonymous);
        return Result.ok();
    }

    @PostMapping("/unstar/{id}")
    public Result unstar(@PathVariable String id){
        Anonymous anonymous = anonymousService.getById(id);
        anonymous.setUnstar(anonymous.getUnstar() + 1);
        anonymousService.updateById(anonymous);
        return Result.ok();
    }

    @PostMapping("/findAll")
    public Result findAll(@RequestBody(required = false) AnoQuery anoQuery){
        List<Anonymous> list = anonymousService.findAll(anoQuery);
        return Result.ok().data(list);
    }

}

