package com.wjw.job.entity.vo;

import com.wjw.job.entity.User;

/**
 * @author wjw
 * @date 2021/5/25 18:41
 */
public class UserInfoVO extends User {

    private String oldPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
