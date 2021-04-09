package com.wjw.job.service;

import com.wjw.job.entity.Role;
import com.wjw.job.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjw
 * @since 2021-01-21
 */
@Service
@Transactional
public class RoleService extends ServiceImpl<RoleMapper, Role>{

    @Autowired
    private RoleMapper roleMapper;

}
