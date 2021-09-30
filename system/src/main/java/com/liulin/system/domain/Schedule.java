package com.liulin.system.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * schedule对象 schedule
 * 
 * @author liulin
 * @date 2021-09-09
 */
public class Schedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public static final Integer ACTIVE = 1;

    public static final Integer INACTIVE = 2;

    public static final Integer MONTHLY = 1;

    public static final Integer QUARTERLY = 2;

    public static final Integer HALF_YEARLY = 3;

    public static final Integer YEARLY = 4;

    public static final Integer PENDING = 2;

    public static final Integer CLOSED = 3;




    /** Schedule Id */
    private Long schId;

    /** Schedule Name */
    private String schName;

    /** Building Id */
    private Long buildingId;

    /** Description */
    private String description;

    /** Quote Summary */
    private String quoteSummary;

    /** Action Required */
    @Excel(name = "Action Required")
    private String actionRequired;

    /** Service */
    @Excel(name = "Service")
    private Long serviceId;

    /** Priority */
    @Excel(name = "Priority")
    private Integer priority;

    /** Frequency */
    @Excel(name = "Frequency")
    private Integer frequency;

    /** Start Date */
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Excel(name = "Start Date", width = 30, dateFormat = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date startDate;

    /** Need Work Order */
    @Excel(name = "Need Work Order")
    private Integer needWorkOrder;

    /** Need Quote */
    @Excel(name = "Need Quote")
    private Integer needQuote;

    /** Status */
    @Excel(name = "Status")
    private Integer status;

    /** order instruction */
    @Excel(name = "order instruction")
    private String orderInstruction;

    /** quote instruction */
    private String quoteInstruction;

    /** order status */
    private Integer orderStatus;

    /** quote status */
    private Integer quoteStatus;

    /** order supplier id */
    private Long orderSupplierId;

    private List<Long> assetIds;

    private List<Long> quoteSupplierIds;

    private String orderSupplierName;

    private String serviceName;

    private String attachmentIds;

    public String getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(String attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public String getQuoteSummary() {
        return quoteSummary;
    }

    public void setQuoteSummary(String quoteSummary) {
        this.quoteSummary = quoteSummary;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getOrderSupplierName() {
        return orderSupplierName;
    }

    public void setOrderSupplierName(String orderSupplierName) {
        this.orderSupplierName = orderSupplierName;
    }

    public List<Long> getQuoteSupplierIds() {
        return quoteSupplierIds;
    }

    public void setQuoteSupplierIds(List<Long> quoteSupplierIds) {
        this.quoteSupplierIds = quoteSupplierIds;
    }

    public List<Long> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<Long> assetIds) {
        this.assetIds = assetIds;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public void setSchId(Long schId)
    {
        this.schId = schId;
    }

    public Long getSchId() 
    {
        return schId;
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
    public void setPriority(Integer priority) 
    {
        this.priority = priority;
    }

    public Integer getPriority() 
    {
        return priority;
    }
    public void setFrequency(Integer frequency) 
    {
        this.frequency = frequency;
    }

    public Integer getFrequency() 
    {
        return frequency;
    }
    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
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
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setOrderInstruction(String orderInstruction) 
    {
        this.orderInstruction = orderInstruction;
    }

    public String getOrderInstruction() 
    {
        return orderInstruction;
    }
    public void setQuoteInstruction(String quoteInstruction) 
    {
        this.quoteInstruction = quoteInstruction;
    }

    public String getQuoteInstruction() 
    {
        return quoteInstruction;
    }
    public void setOrderStatus(Integer orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderStatus() 
    {
        return orderStatus;
    }
    public void setQuoteStatus(Integer quoteStatus) 
    {
        this.quoteStatus = quoteStatus;
    }

    public Integer getQuoteStatus() 
    {
        return quoteStatus;
    }
    public void setOrderSupplierId(Long orderSupplierId) 
    {
        this.orderSupplierId = orderSupplierId;
    }

    public Long getOrderSupplierId() 
    {
        return orderSupplierId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("schId", getSchId())
            .append("description", getDescription())
            .append("actionRequired", getActionRequired())
            .append("serviceId", getServiceId())
            .append("priority", getPriority())
            .append("frequency", getFrequency())
            .append("startDate", getStartDate())
            .append("needWorkOrder", getNeedWorkOrder())
            .append("needQuote", getNeedQuote())
            .append("status", getStatus())
            .append("orderInstruction", getOrderInstruction())
            .append("quoteInstruction", getQuoteInstruction())
            .append("orderStatus", getOrderStatus())
            .append("quoteStatus", getQuoteStatus())
            .append("orderSupplierId", getOrderSupplierId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
