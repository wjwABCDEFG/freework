package com.wjw.job.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjw.common.entity.Result;
import com.wjw.job.entity.Company;
import com.wjw.job.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wjw
 * @date 2021/4/8 0:57
 */
@RestController
@CrossOrigin
@RequestMapping("/job/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/add")
    public Result addCompany(@RequestBody Company company){
        companyService.save(company);
        return Result.ok();
    }

    @DeleteMapping("/remove/{id}")
    public Result removeCompany(@PathVariable Long id){
        companyService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result updateCompany(@RequestBody Company company){
        companyService.updateById(company);
        return Result.ok();
    }

    @GetMapping("/findPage/{pageNum}/{pageSize}")
    public Result findAllCompany(@PathVariable long pageNum, @PathVariable long pageSize){
        Page<Company> pageInfo = new Page<>(pageNum, pageSize);
        companyService.page(pageInfo, null);
        return Result.ok().data(pageInfo);
    }
}
