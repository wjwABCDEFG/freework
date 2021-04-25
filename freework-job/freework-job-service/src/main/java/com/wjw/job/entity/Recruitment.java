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
    private Long companyId;             // 企业ID
    private String address;             // 地址
    private String detailAddress;       // 详细地址
    private String position;            // 职位
    private String salary;              // 薪资范围，单位k
    private Integer salaryMonth;        // 薪资月，几薪
    private String welfareTag;          // 福利标签
    private String workDay;             // 岗位工作日
    private String workTime;            // 岗位工作时间
    private String experienceReq;       // 经验要求
    private String degreeReq;           // 学历要求
    private boolean isCampus;           // 是否校招
    private String positionDesc;        // 职位描述
    private Long hrId;                  // hrID
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Integer getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(Integer salaryMonth) {
        this.salaryMonth = salaryMonth;
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

    public Long getHrId() {
        return hrId;
    }

    public void setHrId(Long hrId) {
        this.hrId = hrId;
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

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
}
