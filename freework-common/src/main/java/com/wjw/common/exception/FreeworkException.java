package com.wjw.common.exception;

import com.wjw.common.enums.ErrCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义可抛出异常
 * @author wjw
 * @date 2021/1/21 21:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreeworkException extends RuntimeException {

    private Integer code;
    private String msg;

    public FreeworkException(ErrCodeEnum errCodeEnum){
        this.code = errCodeEnum.getErrCode();
        this.msg = errCodeEnum.getErrMsg();
    }
}
