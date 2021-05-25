package com.wjw.job.controller;

import com.wjw.common.entity.Result;
import com.wjw.common.enums.ErrCodeEnum;
import com.wjw.job.entity.Company;
import com.wjw.job.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wjw
 * @date 2021/4/9 18:48
 */
@RestController
@RequestMapping("/job/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(String username, String password){
        return Result.ok().data("token", "123123");
    }

    @GetMapping("/info")
    public Result getInfo(){
        return Result.ok().data("roles", "[admin]").data("name", "admin");
    }

    @PostMapping("/becomeHR")
    public Result becomeHR(@RequestParam String companyName,
                           @RequestParam String companyId,
                           @RequestParam String hrId){
        boolean res = userService.becomeHR(companyName, companyId, hrId);
        if (res) return Result.ok();
        else {
            return new Result(ErrCodeEnum.JOINHRERR.getErrCode(), ErrCodeEnum.JOINHRERR.getErrMsg());
        }
    }
}
