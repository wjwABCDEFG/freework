package com.wjw.job.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.common.enums.ErrCodeEnum;
import com.wjw.common.exception.FreeworkException;
import com.wjw.job.entity.Company;
import com.wjw.job.entity.vo.CompanyQuery;
import com.wjw.job.mapper.CompanyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wjw
 * @date 2021/4/7 19:20
 */
@Service
@Transactional
public class CompanyService extends ServiceImpl<CompanyMapper, Company> {

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 条件和分页
     * @param pageInfo 分页
     * @param companyQuery 条件
     */
    public void pageQuery(Page<Company> pageInfo, CompanyQuery companyQuery){
        if (companyQuery == null){
            companyMapper.selectPage(pageInfo, null);
            return ;
        }
        QueryWrapper<Company> wrapper = new QueryWrapper<>();

        String companyName = companyQuery.getCompanyName();
        String address = companyQuery.getAddress();
        String industry = companyQuery.getIndustry();
        if (StringUtils.isNotEmpty(companyName)) wrapper.like("company_name", companyName);
        if (StringUtils.isNotEmpty(address)) wrapper.like("address", address);
        if (StringUtils.isNotEmpty(industry)) wrapper.eq("industry", industry);

        companyMapper.selectPage(pageInfo, wrapper);
    }

    /**
     * 发布招聘信息时需要查询公司列表
     * 只需查询id和名称
     * @return
     */
    public List<Company> findNameAndId() {
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.select("id", "company_name");
        List<Company> companyList = companyMapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(companyList)) throw new FreeworkException(ErrCodeEnum.NONECOMPANYINFO);
        return companyList;
    }
}
