package com.wjw.job.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.job.entity.Recruitment;
import com.wjw.job.mapper.RecruitmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjw
 * @date 2021/4/7 19:20
 */
@Service
@Transactional
public class RecruitmentService extends ServiceImpl<RecruitmentMapper, Recruitment> {

    @Autowired
    private RecruitmentMapper recruitmentMapper;
}
