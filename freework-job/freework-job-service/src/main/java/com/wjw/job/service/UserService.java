package com.wjw.job.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.common.enums.ErrCodeEnum;
import com.wjw.common.exception.FreeworkException;
import com.wjw.job.entity.Company;
import com.wjw.job.entity.User;
import com.wjw.job.mapper.CompanyMapper;
import com.wjw.job.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjw
 * @date 2021/4/7 19:20
 */
@Service
@Transactional
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CompanyMapper companyMapper;

    public boolean becomeHR(String companyName, String companyId, String hrId) {
        Company company = companyMapper.selectById(companyId);
        if (company == null){
            throw new FreeworkException(ErrCodeEnum.NONECOMPANYINFO);
        }
        if (!company.getCompanyName().equals(companyName)){
            return false;
        }
        User hr = new User();
        hr.setId(Long.parseLong(hrId));
        hr.setCompanyId(Long.parseLong(companyId));
        hr.setCompany(companyName);
        int i = userMapper.updateById(hr);
        return i > 0;
    }
}
