package com.wjw.common.enums;

/**
 * 自定义错误码
 * @author wjw
 * @date 2021/1/21 18:18
 */
public enum ErrCodeEnum {

    SUCCESS(2000, "成功"),
    ERROR(5000, "失败"),

    ;

    private Integer errCode;
    private String errMsg;

    ErrCodeEnum(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
