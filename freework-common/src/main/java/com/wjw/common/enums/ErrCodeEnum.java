package com.wjw.common.enums;

/**
 * 自定义错误码
 * @author wjw
 * @date 2021/1/21 18:18
 */
public enum ErrCodeEnum {

    SUCCESS(2000, "成功"),
    ERROR(5000, "失败"),

    //======================================================================登录相关6000
    TOKENINVALID(6001, "登录令牌无效"),
    TOKENEXPIRED(6002, "登录信息过期"),

    //======================================================================用户相关7000

    //======================================================================角色相关8000
    NONEUSERINFO(8001, "未查询到对应用户信息"),

    //======================================================================企业相关9000
    NONECOMPANYINFO(9001, "未查询到对应企业信息"),

    //======================================================================招聘信息相关10000
    NONEJOBINFO(10001, "未查询到任何招聘信息"),

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
