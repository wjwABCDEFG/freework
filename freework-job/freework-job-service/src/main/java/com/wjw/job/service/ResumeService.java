package com.wjw.job.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.job.entity.Resume;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wjw.job.entity.vo.RecruitmentVO;
import com.wjw.job.mapper.ResumeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
import java.util.List;

/**
 * @author wjw
 * @date 2021/05/06 20:04
 */
@Service
@Transactional
public class ResumeService extends ServiceImpl<ResumeMapper, Resume> {

    @Autowired
    private ResumeMapper resumeMapper;

    public List<Resume> findByUser(long userId) {
        QueryWrapper<Resume> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return resumeMapper.selectList(wrapper);
    }
}