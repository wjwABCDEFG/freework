package com.wjw.job.mapper;

import com.wjw.job.entity.Resume;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjw.job.entity.vo.ResumeVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wjw
 * @date 2021/05/06 20:04
 */
@Repository
public interface ResumeMapper extends BaseMapper<Resume> {

    List<ResumeVO> findAll();
}
