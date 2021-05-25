package com.wjw.job.controller;


import com.wjw.common.entity.Result;
import com.wjw.job.entity.Resume;
import com.wjw.job.entity.vo.RecruitmentVO;
import com.wjw.job.entity.vo.ResumeVO;
import com.wjw.job.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wjw
 * @date 2021/05/06 20:04
 */
@RestController
@RequestMapping("/job/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/add")
    public Result addResume(@RequestBody Resume resume){
        resumeService.save(resume);
        return Result.ok();
    }

    @DeleteMapping("/remove/{id}")
    public Result removeResume(@PathVariable Long id){
        resumeService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result updateResume(@RequestBody Resume resume){
        resumeService.updateById(resume);
        return Result.ok();
    }

    @GetMapping("/findAll")
    public Result findAllResume(){
        List<ResumeVO> resumeList = resumeService.findAll();
        return Result.ok().data(resumeList);
    }

    @GetMapping("/findByUser/{userId}")
    public Result findByUser(@PathVariable String userId){
        List<Resume> resumeList = resumeService.findByUser(userId);
        if (CollectionUtils.isEmpty(resumeList)) return Result.ok().msg("立刻开始创建自己的简历吧！");
        return Result.ok().data(resumeList);
    }

    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Long id){
        Resume resume = resumeService.getById(id);
        return Result.ok().data(resume);
    }

    @PostMapping("/removeBatch")
    public Result removeBatch(@RequestBody List<Long> rids){
        resumeService.removeByIds(rids);
        return Result.ok();
    }

    /**
     * 不包括简历内容
     * @param userId
     * @return
     */
    @GetMapping("/findSimpleByUser/{userId}")
    public Result findSimpleByUser(@PathVariable String userId){
        List<Resume> resumeList = resumeService.findSimpleByUser(userId);
        if (CollectionUtils.isEmpty(resumeList)) return Result.ok().msg("立刻开始创建自己的简历吧！");
        return Result.ok().data(resumeList);
    }
}

