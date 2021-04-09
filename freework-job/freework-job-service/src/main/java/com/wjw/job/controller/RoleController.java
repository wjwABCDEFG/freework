package com.wjw.job.controller;


import com.wjw.common.entity.Result;
import com.wjw.job.entity.Role;
import com.wjw.job.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wjw
 * @since 2021-01-21
 */
@RestController
@CrossOrigin
@RequestMapping("/job/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    public Result addRole(@RequestBody Role role){
        roleService.save(role);
        return Result.ok();
    }

    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id){
        roleService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Role role){
        roleService.updateById(role);
        return Result.ok();
    }

    @GetMapping("/findAll")
    public Result<List<Role>> findAll(){
        List<Role> list = roleService.list(null);
        return new Result<>(200, "成功", list);
    }
}

