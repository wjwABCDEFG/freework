package com.wjw.job.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjw.common.entity.Result;
import com.wjw.common.enums.ErrCodeEnum;
import com.wjw.job.entity.Company;
import com.wjw.job.entity.User;
import com.wjw.job.entity.vo.CompanyQuery;
import com.wjw.job.entity.vo.UserInfoVO;
import com.wjw.job.entity.vo.UserQuery;
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

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody UserInfoVO userInfoVO){
        userService.updateUser(userInfoVO);
        return Result.ok();
    }

    @PostMapping("/findById/{id}")
    public Result findById(@PathVariable String id){
        User user = userService.getById(id);
        return Result.ok().data(user);
    }

    @DeleteMapping("/remove/{id}")
    public Result delUser(@PathVariable Long id){
        userService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/findPage/{pageNum}/{pageSize}")
    public Result pageUser(@PathVariable long pageNum,
                              @PathVariable long pageSize,
                              @RequestBody(required = false) UserQuery userQuery){
        Page<User> pageInfo = new Page<>(pageNum, pageSize);
        userService.pageQuery(pageInfo, userQuery);
        return Result.ok().data(pageInfo);
    }
}
