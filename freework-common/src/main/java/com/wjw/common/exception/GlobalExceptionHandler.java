package com.wjw.common.exception;

import com.wjw.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常
 * @author wjw
 * @date 2021/1/21 19:37
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(FreeworkException.class)
    @ResponseBody
    public Result error(FreeworkException e){
        e.printStackTrace();
        log.error(e.getMessage());
        return Result.error().code(e.getCode()).msg(e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());
        return Result.error();
    }
}
