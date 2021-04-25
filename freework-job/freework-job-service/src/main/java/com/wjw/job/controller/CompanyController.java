package com.wjw.job.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjw.common.entity.Result;
import com.wjw.common.utils.JsonUtil;
import com.wjw.job.entity.Company;
import com.wjw.job.entity.vo.CompanyQuery;
import com.wjw.job.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/findPage/{pageNum}/{pageSize}")
    public Result pageCompany(@PathVariable long pageNum,
                              @PathVariable long pageSize,
                              @RequestBody(required = false) CompanyQuery companyQuery){
        Page<Company> pageInfo = new Page<>(pageNum, pageSize);
        companyService.pageQuery(pageInfo, companyQuery);
        return Result.ok().data(pageInfo);
    }

    @PostMapping("/findById/{id}")
    public Result findCompanyById(@PathVariable Long id){
        Company company = companyService.getById(id);
        return Result.ok().data(company);
    }

    @GetMapping("/static")
    public Result updateCompany(String fileName){
        String res = JsonUtil.readJsonStrFromFile(fileName);
        return Result.ok().data(res);
    }

    @GetMapping("/findNameAndId")
    public Result findNameAndId(){
        List<Company> companies = this.companyService.findNameAndId();
        return Result.ok().data(companies);
    }
}
