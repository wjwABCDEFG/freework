package com.wjw.job.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjw.common.entity.Result;
import com.wjw.job.constant.Progress;
import com.wjw.job.entity.Company;
import com.wjw.job.entity.Delivery;
import com.wjw.job.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wjw
 * @since 2021-05-16
 */
@RestController
@CrossOrigin
@RequestMapping("/job/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/deliResume")
    public Result delivery(@RequestParam("userId") String userId,
                           @RequestParam("recruitmentId") String recruitmentId,
                           @RequestParam("resumeId") String resumeId){
        deliveryService.delivery(Long.parseLong(userId), Long.parseLong(recruitmentId),
                Long.parseLong(resumeId), Progress.START);
        return Result.ok();
    }

    @PostMapping("/updateProgress")
    public Result updateProgress(@RequestParam("id") Long id,
                                 @RequestParam("progress") Integer progress,
                                 @RequestParam("msg") String msg){
        deliveryService.updateProgress(id, progress, msg);
        return Result.ok();
    }

    @GetMapping("/pageCandidates/{pageNum}/{pageSize}")
    public Result pageCandidates(@PathVariable long pageNum,
                                 @PathVariable long pageSize){
        Page<Delivery> pageInfo = new Page<>(pageNum, pageSize);
        deliveryService.page(pageInfo, null);
        return Result.ok().data(pageInfo);
    }

    @GetMapping("/pageCandidatesByRec/{recruitmentId}/{pageNum}/{pageSize}")
    public Result pageCandidatesByRecruitment(@PathVariable long recruitmentId,
                                              @PathVariable long pageNum,
                                              @PathVariable long pageSize){
        Page<Delivery> pageInfo = deliveryService.pageCandidatesByRecruitment(recruitmentId, pageNum, pageSize);
        return Result.ok().data(pageInfo);
    }
}

