package com.wjw.job.controller;


import com.wjw.common.entity.Result;
import com.wjw.job.entity.Role;
import com.wjw.job.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
    public Result insert(@RequestBody Role role){
        boolean flag = roleService.save(role);
        if (flag) {
            return Result.ok();
        }else {
            return Result.error().msg("插入失败");
        }
    }

    @GetMapping("/findAll")
    public Result<List<Role>> findAll(){
        List<Role> list = roleService.list(null);
        if (!CollectionUtils.isEmpty(list)){
            return new Result<>(200, "成功", list);
        }else {
            return Result.error();
        }
    }

    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id){
        roleService.removeById(id);
        return Result.ok();
    }
}

