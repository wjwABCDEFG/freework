package com.wjw.job.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjw.common.entity.Result;
import com.wjw.job.entity.Company;
import com.wjw.job.entity.Recruitment;
import com.wjw.job.entity.vo.CompanyQuery;
import com.wjw.job.entity.vo.RecruitmentDetailVO;
import com.wjw.job.entity.vo.RecruitmentQuery;
import com.wjw.job.entity.vo.RecruitmentVO;
import com.wjw.job.service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author wjw
 * @date 2021/4/20 2:07
 */
@RestController
@CrossOrigin
@RequestMapping("/job/recruitment")
public class RecruitmentController {

    @Autowired
    private RecruitmentService recruitmentService;

    @PostMapping("/add")
    public Result addRecruitment(@RequestBody Recruitment recruitment){
        recruitmentService.save(recruitment);
        return Result.ok();
    }

    @DeleteMapping("/remove/{id}")
    public Result removeRecruitment(@PathVariable long id){
        recruitmentService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result updateRecruitment(@RequestBody Recruitment recruitment){
        recruitmentService.updateById(recruitment);
        return Result.ok();
    }

    @GetMapping("/findAll")
    public Result findAllRecruitment(){
        List<RecruitmentVO> recruitmentList = recruitmentService.findAll();
        return Result.ok().data(recruitmentList);
    }

    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable long id){
        RecruitmentDetailVO detailVO = recruitmentService.findById(id);
        return Result.ok().data(detailVO);
    }

    @PostMapping("/removeBatch")
    public Result removeBatch(@RequestBody List<Long> rids){
        recruitmentService.removeByIds(rids);
        return Result.ok();
    }

    /**
     * 展示页首页固定展示8条信息
     */
    @GetMapping("/findIndex")
    public Result findIndex(){
        List<RecruitmentVO> recruitmentList = recruitmentService.findIndex();
        return Result.ok().data(recruitmentList);
    }

    @PostMapping("/findPage/{pageNum}/{pageSize}")
    public Result pageRecruitment(@PathVariable long pageNum,
                                  @PathVariable long pageSize,
                                  @RequestBody(required = false) RecruitmentQuery recruitmentQuery){
        Page<Recruitment> pageInfo = new Page<>(pageNum, pageSize);
        Map<String, Object> res = recruitmentService.pageQuery(pageInfo, recruitmentQuery);
        return Result.ok().data(res);
    }

    @GetMapping("/findByCompanyId/{companyId}")
    public Result findByCompanyId(@PathVariable String companyId){
        List<RecruitmentVO> recruitmentList = recruitmentService.findByCompanyId(companyId);
        return Result.ok().data(recruitmentList);
    }
}
