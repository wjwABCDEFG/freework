package com.wjw.user.controller;


import com.wjw.common.entity.Result;
import com.wjw.common.enums.ErrCodeEnum;
import com.wjw.common.exception.FreeworkException;
import com.wjw.common.utils.JwtUtils;
import com.wjw.user.entity.User;
import com.wjw.user.entity.vo.LoginVo;
import com.wjw.user.entity.vo.registerVO;
import com.wjw.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wjw
 * @since 2021-05-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        String token = userService.login(loginVo);
        return Result.ok().data("token", token);
    }

    @PostMapping("/register")
    public Result register(@RequestBody registerVO registerVO){
        userService.register(registerVO);
        return Result.ok();
    }

    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        try {
            String userId = JwtUtils.getUserIdByJwtToken(request);
            User user = userService.getById(userId);
            user.setPassword("");
            return Result.ok().data(user);
        }catch (Exception e){
            e.printStackTrace();
            throw new FreeworkException(ErrCodeEnum.TOKENEXPIRED);
        }
    }
}

