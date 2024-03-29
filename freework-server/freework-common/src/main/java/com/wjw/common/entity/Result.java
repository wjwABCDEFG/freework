package com.wjw.common.entity;

import com.wjw.common.enums.ErrCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wjw
 * @date 2021/1/21 17:30
 */
@Data
public class Result {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "消息提示")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private Object data;

    public Result() {
    }

    public Result(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //支持一下链式编程
    public static Result ok(){
        return new Result(ErrCodeEnum.SUCCESS.getErrCode(), ErrCodeEnum.SUCCESS.getErrMsg());
    }

    public static Result error(){
        return new Result(ErrCodeEnum.ERROR.getErrCode(), ErrCodeEnum.ERROR.getErrMsg());
    }

    public Result msg(String msg){
        this.setMsg(msg);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(Object data){
        this.data = data;
        return this;
    }

    public Result data(String key, String value){
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        this.data = map;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
