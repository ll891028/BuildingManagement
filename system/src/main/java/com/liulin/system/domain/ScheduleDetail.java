package com.liulin.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * scheduleDetail对象 schedule_detail
 * 
 * @author liulin
 * @date 2021-09-22
 */
public class ScheduleDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Schedule Detail Id */
    private Long schDetailId;

    /** Schedule Id */
    private Long schId;

    /** Date Schedule */
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Excel(name = "Schedule Date", width = 30, dateFormat = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date schDate;

    /** Time Complete */
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Excel(name = "Date Complete", width = 30, dateFormat = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date completeDate;

    /** PM Schedule Status */
    @Excel(name = "PM Schedule Status")
    private Integer status;

    /** Attachment */
    private String attachmentIds;

    private String schName;

    private String serviceName;

    private Integer priority;

    private Integer frequency;

    private Integer needWorkOrder;

    private Integer needQuote;

    private String description;

    private Long buildingId;

    private Long companyId;

    private String schDetailNo;

    public String getSchDetailNo() {
        return schDetailNo;
    }

    public void setSchDetailNo(String schDetailNo) {
        this.schDetailNo = schDetailNo;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getNeedWorkOrder() {
        return needWorkOrder;
    }

    public void setNeedWorkOrder(Integer needWorkOrder) {
        this.needWorkOrder = needWorkOrder;
    }

    public Integer getNeedQuote() {
        return needQuote;
    }

    public void setNeedQuote(Integer needQuote) {
        this.needQuote = needQuote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSchDetailId(Long schDetailId)
    {
        this.schDetailId = schDetailId;
    }

    public Long getSchDetailId() 
    {
        return schDetailId;
    }
    public void setSchId(Long schId) 
    {
        this.schId = schId;
    }

    public Long getSchId() 
    {
        return schId;
    }
    public void setSchDate(Date schDate) 
    {
        this.schDate = schDate;
    }

    public Date getSchDate() 
    {
        return schDate;
    }
    public void setCompleteDate(Date completeDate) 
    {
        this.completeDate = completeDate;
    }

    public Date getCompleteDate() 
    {
        return completeDate;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setAttachmentIds(String attachmentIds) 
    {
        this.attachmentIds = attachmentIds;
    }

    public String getAttachmentIds() 
    {
        return attachmentIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("schDetailId", getSchDetailId())
            .append("schId", getSchId())
            .append("schDate", getSchDate())
            .append("completeDate", getCompleteDate())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("attachmentIds", getAttachmentIds())
            .toString();
    }
}
