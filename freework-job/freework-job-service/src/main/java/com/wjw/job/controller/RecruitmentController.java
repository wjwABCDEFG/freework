package com.wjw.job.controller;

import com.wjw.common.entity.Result;
import com.wjw.job.entity.Recruitment;
import com.wjw.job.entity.vo.RecruitmentDetailVO;
import com.wjw.job.entity.vo.RecruitmentVO;
import com.wjw.job.service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Result removeRecruitment(@PathVariable Long id){
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
    public Result findById(@PathVariable Long id){
        RecruitmentDetailVO detailVO = recruitmentService.findById(id);
        return Result.ok().data(detailVO);
    }

    @PostMapping("/removeBatch")
    public Result removeBatch(@RequestBody List<Long> rids){
        recruitmentService.removeByIds(rids);
        return Result.ok();
    }
}
