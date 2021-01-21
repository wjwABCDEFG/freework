package com.wjw.job.controller;


import com.wjw.job.entity.Role;
import com.wjw.job.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wjw
 * @since 2021-01-21
 */
@RestController
@RequestMapping("/job/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/add")
    public String insert(@RequestBody Role role){
        roleService.save(role);
        return "成功";
    }

    @GetMapping("/findAll")
    public List<Role> findAll(){
        List<Role> list = roleService.list(null);
        return list;
    }
}

