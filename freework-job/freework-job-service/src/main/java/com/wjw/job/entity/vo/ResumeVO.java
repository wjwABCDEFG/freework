package com.wjw.job.entity.vo;

import com.wjw.job.entity.Resume;
import com.wjw.job.entity.User;

/**
 * @author wjw
 * @date 2021/05/06 20:04
 */
public class ResumeVO extends Resume {

    private String userName;

    private User user;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
