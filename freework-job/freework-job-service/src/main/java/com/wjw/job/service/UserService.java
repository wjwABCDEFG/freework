package com.wjw.job.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.common.enums.ErrCodeEnum;
import com.wjw.common.exception.FreeworkException;
import com.wjw.common.utils.MD5;
import com.wjw.job.constant.CompanyAuth;
import com.wjw.job.entity.Company;
import com.wjw.job.entity.User;
import com.wjw.job.entity.vo.CompanyQuery;
import com.wjw.job.entity.vo.UserInfoVO;
import com.wjw.job.entity.vo.UserQuery;
import com.wjw.job.mapper.CompanyMapper;
import com.wjw.job.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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

    public void updateUser(UserInfoVO userInfoVO) {
        User store = userMapper.selectById(userInfoVO.getId());
        String newPassword;
        if (!StringUtils.isBlank(userInfoVO.getOldPassword()) && !StringUtils.isBlank(userInfoVO.getPassword())){
            //试图修改密码
            if (!MD5.encrypt(userInfoVO.getOldPassword()).equals(store.getPassword())){
                throw new FreeworkException(ErrCodeEnum.LOGINERR);
            }
            newPassword = MD5.encrypt(userInfoVO.getPassword());
        }else{
            // 不修改密码
            newPassword = store.getPassword();
        }
        User user = new User();
        BeanUtils.copyProperties(userInfoVO, user);
        user.setPassword(newPassword);
        userMapper.updateById(user);
    }

    public void pageQuery(Page<User> pageInfo, UserQuery userQuery){
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        if (userQuery == null){
            userMapper.selectPage(pageInfo, wrapper);
            return ;
        }

        String userName = userQuery.getName();
        String phone = userQuery.getPhone();
        if (StringUtils.isNotEmpty(userName)) wrapper.like("name", userName);
        if (StringUtils.isNotEmpty(phone)) wrapper.like("phone", phone);


        userMapper.selectPage(pageInfo, wrapper);
    }
}
