package com.liulin.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;
import com.liulin.common.utils.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: LinLiu
 * @Date: 2021/9/26 9:58 上午
 */
public class ReportDto extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String TASK = "1";

    public static final String INCIDENT = "2";

    public static final String PM_SCHEDULE = "3";

    private Integer row;

    private String name;

    private String description;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateRaised;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateComplete;

    private Integer status;

    private String attachments;

    private String attachmentIds;

    private String service;

    private String taskType;

    private String taskTypeText;

    private Long buildingId;

    public String getTaskTypeText() {
        if(StringUtils.isNotEmpty(taskType)){
            switch (taskType){
                case TASK:
                    return "Task";
                case INCIDENT:
                    return "Incident";
                case PM_SCHEDULE:
                    return "PM Schedule";
                default:
                    return "";
            }
        }
        return taskTypeText;
    }

    public void setTaskTypeText(String taskTypeText) {
        this.taskTypeText = taskTypeText;
    }

    public Date getDateComplete() {
        return dateComplete;
    }

    public void setDateComplete(Date dateComplete) {
        this.dateComplete = dateComplete;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(String attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateRaised() {
        return dateRaised;
    }

    public void setDateRaised(Date dateRaised) {
        this.dateRaised = dateRaised;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }
}
