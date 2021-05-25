package com.wjw.job.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.common.enums.ErrCodeEnum;
import com.wjw.common.exception.FreeworkException;
import com.wjw.common.utils.MailUtils;
import com.wjw.job.constant.CompanyAuth;
import com.wjw.job.entity.Company;
import com.wjw.job.entity.User;
import com.wjw.job.entity.vo.CompanyQuery;
import com.wjw.job.mapper.CompanyMapper;
import com.wjw.job.mapper.UserMapper;
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

    @Autowired
    private UserMapper userMapper;

    /**
     * 条件和分页
     * @param pageInfo 分页
     * @param companyQuery 条件
     */
    public void pageQuery(Page<Company> pageInfo, CompanyQuery companyQuery){
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.eq("auth", CompanyAuth.ALLOW);

        if (companyQuery == null){
            companyMapper.selectPage(pageInfo, wrapper);
            return ;
        }

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

    public void findNotAllow(Page<Company> pageInfo) {
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.eq("auth", CompanyAuth.NOT_ALLOW);
        companyMapper.selectPage(pageInfo, wrapper);
    }

    public List<Company> findIndex() {
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        wrapper.eq("auth", CompanyAuth.ALLOW);
        return companyMapper.selectList(wrapper);
    }

    public void check(Company company) {
        companyMapper.updateById(company);
        User basicHr = userMapper.findByCompanyId(company.getId());
        // 发送邮件
        StringBuilder msg = new StringBuilder();
        String genderCall = basicHr.getGender() == 0 ? "先生" : "女士";
        msg.append("尊敬的").append(basicHr.getName()).append(genderCall)
                .append("你好，您的企业认证以通过。以下是您的企业加入码，请妥善保存：")
                .append(company.getId());
        MailUtils.sendMail(basicHr.getEmail(), msg.toString(), "[freework]企业审核通过提醒");
    }
}
