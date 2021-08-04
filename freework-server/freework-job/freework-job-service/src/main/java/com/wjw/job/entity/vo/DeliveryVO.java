package com.wjw.job.entity.vo;

import com.wjw.job.entity.Delivery;
import com.wjw.job.entity.User;

/**
 * @author wjw
 * @date 2021/5/24 17:55
 */
public class DeliveryVO extends Delivery {

    private String position;
    private User user;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
