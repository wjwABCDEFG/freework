package com.wjw.job.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.job.entity.User;
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
}
