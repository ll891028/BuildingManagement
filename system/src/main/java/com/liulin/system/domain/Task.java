package com.liulin.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * Task对象 task
 * 
 * @author liulin
 * @date 2021-08-19
 */
public class Task extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Task Id */
    private Long taskId;

    /** Description */
    @Excel(name = "Description")
    private String description;

    /** Action Required */
    @Excel(name = "Action Required")
    private String actionRequired;

    /** Service */
    @Excel(name = "Service")
    private Long serviceId;

    /** Date Raised */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Date Raised", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dateRaised;

    /** Time scheduled */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Time scheduled", width = 30, dateFormat = "yyyy-MM-dd")
    private Date timeScheduled;

    /** Priority */
    @Excel(name = "Priority")
    private Integer priority;

    /** Need Work Order */
    @Excel(name = "Need Work Order")
    private Integer needWorkOrder;

    /** Need Quote */
    @Excel(name = "Need Quote")
    private Integer needQuote;

    /** Status */
    @Excel(name = "Status")
    private Integer taskStatus;

    /** Attachment */
    @Excel(name = "Attachment")
    private String attachmentIds;

    private String assetIds;

    private Long buildingId;

    private String orderInstruction;

    private String quoteInstruction;

    private Integer orderStatus;

    private Integer quoteStatus;

    private Long orderSupplierId;

    public Long getOrderSupplierId() {
        return orderSupplierId;
    }

    public void setOrderSupplierId(Long orderSupplierId) {
        this.orderSupplierId = orderSupplierId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getQuoteStatus() {
        return quoteStatus;
    }

    public void setQuoteStatus(Integer quoteStatus) {
        this.quoteStatus = quoteStatus;
    }

    public String getOrderInstruction() {
        return orderInstruction;
    }

    public void setOrderInstruction(String orderInstruction) {
        this.orderInstruction = orderInstruction;
    }

    public String getQuoteInstruction() {
        return quoteInstruction;
    }

    public void setQuoteInstruction(String quoteInstruction) {
        this.quoteInstruction = quoteInstruction;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(String assetIds) {
        this.assetIds = assetIds;
    }

    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setActionRequired(String actionRequired) 
    {
        this.actionRequired = actionRequired;
    }

    public String getActionRequired() 
    {
        return actionRequired;
    }
    public void setServiceId(Long serviceId) 
    {
        this.serviceId = serviceId;
    }

    public Long getServiceId() 
    {
        return serviceId;
    }
    public void setDateRaised(Date dateRaised) 
    {
        this.dateRaised = dateRaised;
    }

    public Date getDateRaised() 
    {
        return dateRaised;
    }
    public void setTimeScheduled(Date timeScheduled) 
    {
        this.timeScheduled = timeScheduled;
    }

    public Date getTimeScheduled() 
    {
        return timeScheduled;
    }
    public void setPriority(Integer priority) 
    {
        this.priority = priority;
    }

    public Integer getPriority() 
    {
        return priority;
    }
    public void setNeedWorkOrder(Integer needWorkOrder) 
    {
        this.needWorkOrder = needWorkOrder;
    }

    public Integer getNeedWorkOrder() 
    {
        return needWorkOrder;
    }
    public void setNeedQuote(Integer needQuote) 
    {
        this.needQuote = needQuote;
    }

    public Integer getNeedQuote() 
    {
        return needQuote;
    }
    public void setTaskStatus(Integer taskStatus) 
    {
        this.taskStatus = taskStatus;
    }

    public Integer getTaskStatus() 
    {
        return taskStatus;
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
            .append("taskId", getTaskId())
            .append("description", getDescription())
            .append("actionRequired", getActionRequired())
            .append("serviceId", getServiceId())
            .append("dateRaised", getDateRaised())
            .append("timeScheduled", getTimeScheduled())
            .append("priority", getPriority())
            .append("needWorkOrder", getNeedWorkOrder())
            .append("needQuote", getNeedQuote())
            .append("taskStatus", getTaskStatus())
            .append("attachmentIds", getAttachmentIds())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
