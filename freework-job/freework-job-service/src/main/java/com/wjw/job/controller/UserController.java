package com.wjw.job.controller;

import com.wjw.common.entity.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @author wjw
 * @date 2021/4/9 18:48
 */
@RestController
@CrossOrigin
@RequestMapping("/job/user")
public class UserController {

    @PostMapping("/login")
    public Result login(String username, String password){
        return Result.ok().data("token", "123123");
    }

    @GetMapping("/info")
    public Result getInfo(){
        return Result.ok().data("roles", "[admin]").data("name", "admin");
    }
}
