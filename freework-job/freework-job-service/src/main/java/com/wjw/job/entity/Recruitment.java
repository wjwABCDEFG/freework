package com.wjw.job.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author wjw
 * @date 2021/4/20 1:46
 */
@TableName("tb_recruitment")
public class Recruitment extends BaseDomain {

    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;                    // 招聘信息ID
    private Company company;            // 企业
    private String address;             // 职位
    private String position;            // 行业
    private String salary;              // 薪资范围，单位k
    private Integer salary_month;       // 薪资月，几薪
    private String welfareTag;          // 福利标签
    private String workDay;             // 岗位工作日
    private String workTime;            // 岗位工作时间
    private String experienceReq;       // 经验要求
    private String degreeReq;           // 学历要求
    private boolean isCampus;           // 是否校招
    private String positionDesc;        // 职位描述
    private User hr;                  // hr
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;               // 截止时间
    @TableLogic
    private Integer status;             // 可用状态

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Integer getSalary_month() {
        return salary_month;
    }

    public void setSalary_month(Integer salary_month) {
        this.salary_month = salary_month;
    }

    public String getWelfareTag() {
        return welfareTag;
    }

    public void setWelfareTag(String welfareTag) {
        this.welfareTag = welfareTag;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getExperienceReq() {
        return experienceReq;
    }

    public void setExperienceReq(String experienceReq) {
        this.experienceReq = experienceReq;
    }

    public String getDegreeReq() {
        return degreeReq;
    }

    public void setDegreeReq(String degreeReq) {
        this.degreeReq = degreeReq;
    }

    public boolean isCampus() {
        return isCampus;
    }

    public void setCampus(boolean campus) {
        isCampus = campus;
    }

    public String getPositionDesc() {
        return positionDesc;
    }

    public void setPositionDesc(String positionDesc) {
        this.positionDesc = positionDesc;
    }

    public User getHr() {
        return hr;
    }

    public void setHr(User hr) {
        this.hr = hr;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
