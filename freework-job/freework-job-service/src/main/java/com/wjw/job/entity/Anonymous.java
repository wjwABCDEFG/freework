package com.wjw.job.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author wjw
 * @since 2021-05-26
 */
@TableName("tb_anonymous")
@ApiModel(value="Anonymous对象", description="")
public class Anonymous extends BaseDomain {

    @ApiModelProperty(value = "轮播图ID")
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    private String position;

    private String city;

    @ApiModelProperty(value = "薪资范围，单位k")
    private String salary;

    @ApiModelProperty(value = "薪资月，几薪")
    private Integer salaryMonth;

    @ApiModelProperty(value = "点赞数量")
    private Integer star;

    @ApiModelProperty(value = "点踩数量")
    private Integer unstar;

    @ApiModelProperty(value = "0可用 1不可用")
    @TableLogic
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getUnstar() {
        return unstar;
    }

    public void setUnstar(Integer unstar) {
        this.unstar = unstar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Anonymous{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", position='" + position + '\'' +
                ", city='" + city + '\'' +
                ", salary='" + salary + '\'' +
                ", salaryMonth=" + salaryMonth +
                ", star=" + star +
                ", unstar=" + unstar +
                ", status=" + status +
                '}';
    }
}
