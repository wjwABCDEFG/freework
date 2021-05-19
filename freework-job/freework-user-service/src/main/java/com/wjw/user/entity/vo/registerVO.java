package com.wjw.user.entity.vo;

import com.wjw.user.entity.User;

/**
 * @author wjw
 * @date 2021/5/19 14:15
 */
public class registerVO extends User {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
