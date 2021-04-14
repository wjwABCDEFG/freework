package com.wjw.job.entity.vo;

/**
 * 企业查询筛选条件
 * @author wjw
 * @date 2021/4/12 16:41
 */
public class CompanyQuery {

    private String companyName;         // 企业名称
    private String industry;            // 行业
    private String address;             // 地点

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
