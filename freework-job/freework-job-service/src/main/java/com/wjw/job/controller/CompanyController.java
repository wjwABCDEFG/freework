package com.wjw.job.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjw.common.entity.Result;
import com.wjw.common.utils.JsonUtil;
import com.wjw.job.entity.Company;
import com.wjw.job.entity.vo.CompanyQuery;
import com.wjw.job.entity.vo.RecruitmentVO;
import com.wjw.job.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wjw
 * @date 2021/4/8 0:57
 */
@RestController
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
    public Result removeCompany(@PathVariable long id){
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
    public Result findCompanyById(@PathVariable String id){
        Company company = companyService.getById(id);
        return Result.ok().data(company);
    }

    /**
     * 根据文件名获取静态资源，比如行业json
     * @param fileName 文件名
     */
    @GetMapping("/static")
    public Result getStatic(String fileName){
        String res = JsonUtil.readJsonStrFromFile(fileName);
        return Result.ok().data(res);
    }

    @GetMapping("/findNameAndId")
    public Result findNameAndId(){
        List<Company> companies = companyService.findNameAndId();
        return Result.ok().data(companies);
    }

    /**
     * 查找待审批列表
     */
    @GetMapping("/findNotAllow/{pageNum}/{pageSize}")
    public Result findNotAllow(@PathVariable long pageNum,
                               @PathVariable long pageSize){
        Page<Company> pageInfo = new Page<>(pageNum, pageSize);
        companyService.findNotAllow(pageInfo);
        return Result.ok().data(pageInfo);
    }

    @PostMapping("/check")
    public Result check(@RequestBody Company company){
        companyService.check(company);
        return Result.ok();
    }

    /**
     * 展示页首页固定展示4条信息
     */
    @GetMapping("/findIndex")
    public Result findIndex(){
        List<Company> companyList = companyService.findIndex();
        return Result.ok().data(companyList);
    }
}
