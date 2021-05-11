package com.wjw.common.enums;

/**
 * 自定义错误码
 * @author wjw
 * @date 2021/1/21 18:18
 */
public enum ErrCodeEnum {

    SUCCESS(2000, "成功"),
    ERROR(5000, "失败"),
    TIMEOUT(5001, "失败，触发服务熔断"),

    //======================================================================登录相关6000
    TOKENINVALID(6001, "登录令牌无效"),
    TOKENEXPIRED(6002, "登录信息过期"),

    //======================================================================用户相关6100

    //======================================================================角色相关6200
    NONEUSERINFO(6201, "未查询到对应用户信息"),

    //======================================================================企业相关6300
    NONECOMPANYINFO(6301, "未查询到对应企业信息"),

    //======================================================================招聘信息相关6400
    NONEJOBINFO(6401, "未查询到任何招聘信息"),

    //======================================================================视频相关6500
    VEDIOUPLOADERR(6501, "上传视频失败"),
    VEDIODELERR(6502, "删除云端视频失败"),

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
