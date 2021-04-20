package com.wjw.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjw.job.entity.Company;
import com.wjw.job.entity.Recruitment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wjw
 * @date 2021/4/20 19:18
 */
@Repository
public interface RecruitmentMapper extends BaseMapper<Recruitment> {

    List<Recruitment> findAll();
}
