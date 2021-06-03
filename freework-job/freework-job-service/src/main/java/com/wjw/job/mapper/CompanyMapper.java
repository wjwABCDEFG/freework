package com.wjw.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjw.job.entity.Company;
import org.springframework.stereotype.Repository;

/**
 * @author wjw
 * @date 2021/4/7 19:18
 */
@Repository
public interface CompanyMapper extends BaseMapper<Company> {

    Company findByName(String companyName);


}
