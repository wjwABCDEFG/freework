package com.wjw.job.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.job.entity.Anonymous;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wjw.job.entity.Company;
import com.wjw.job.entity.vo.AnoQuery;
import com.wjw.job.mapper.AnonymousMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wjw
 * @since 2021-05-26
 */
@Service
@Transactional
public class AnonymousService extends ServiceImpl<AnonymousMapper, Anonymous> {

    @Autowired
    private AnonymousMapper anonymousMapper;

    public List<Anonymous> findAll(AnoQuery anoQuery) {

        if (anoQuery == null){
            return anonymousMapper.selectList(null);
        }

        QueryWrapper<Anonymous> wrapper = new QueryWrapper<>();
        String companyName = anoQuery.getCompanyName();
        if (StringUtils.isNotEmpty(companyName)) wrapper.like("company_name", companyName);

        return anonymousMapper.selectList(wrapper);
    }
}
