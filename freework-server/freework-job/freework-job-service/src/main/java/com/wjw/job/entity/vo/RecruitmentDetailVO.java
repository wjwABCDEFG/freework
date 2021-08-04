package com.wjw.job.entity.vo;

import com.wjw.job.entity.Company;
import com.wjw.job.entity.Recruitment;
import com.wjw.job.entity.User;

/**
 * @author wjw
 * @date 2021/4/21 2:21
 */
public class RecruitmentDetailVO extends Recruitment {

    private Company company;
    private User hr;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getHr() {
        return hr;
    }

    public void setHr(User hr) {
        this.hr = hr;
    }
}
