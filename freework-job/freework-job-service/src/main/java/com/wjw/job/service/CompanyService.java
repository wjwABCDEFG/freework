package com.wjw.job.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.job.entity.Company;
import com.wjw.job.mapper.CompanyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjw
 * @date 2021/4/7 19:20
 */
@Service
@Transactional
public class CompanyService extends ServiceImpl<CompanyMapper, Company> {

}
