package com.wjw.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjw.job.entity.Recruitment;
import com.wjw.job.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author wjw
 * @date 2021/4/20 19:18
 */
@Repository
public interface RecruitmentMapper extends BaseMapper<Recruitment> {

    User findHrById(Long id);
}
