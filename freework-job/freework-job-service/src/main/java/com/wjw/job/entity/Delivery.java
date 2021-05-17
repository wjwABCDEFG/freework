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
 * @since 2021-05-16
 */
@TableName("tb_delivery")
@ApiModel(value="Delivery对象", description="")
public class Delivery extends BaseDomain {

    @ApiModelProperty(value = "投递ID")
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(value = "招聘信息ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long recruitmentId;

    @ApiModelProperty(value = "简历ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long resumeId;

    @ApiModelProperty(value = "求职者ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    @ApiModelProperty(value = "进度")
    private Integer progress;

    @ApiModelProperty(value = "消息")
    private String msg;

    @ApiModelProperty(value = "0可用 1不可用")
    @TableLogic
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(Long recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", recruitmentId=" + recruitmentId +
                ", resumeId=" + resumeId +
                ", userId=" + userId +
                ", progress=" + progress +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                '}';
    }
}
