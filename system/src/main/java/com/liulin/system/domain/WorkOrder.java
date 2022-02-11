package com.liulin.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;
import com.liulin.common.utils.DateUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * Work Order对象 work_order
 * 
 * @author liulin
 * @date 2022-02-08
 */
public class WorkOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Work Order Id */
    private Long workOrderId;

    /** Work Order Number */
    @Excel(name = "Work Order Number")
    private String workOrderNo;

    /** Due By */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Due By", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueBy;

    /** Priority */
    @Excel(name = "Priority")
    private String priority;

    /** Assigned To */
    @Excel(name = "Assigned To")
    private String assignedTo;

    /** Service Name */
    @Excel(name = "Service Name")
    private String serviceName;

    /** Service Id */
    private Long serviceId;

    /** Contact Number */
    private String contactNumber;

    /** Description */
    private String description;

    /** Media1 */
    private String media1;

    /** Media2 */
    private String media2;

    /** Spn Logo */
    private String spnLogo;

    /** Spn Name */
    private String spnName;

    /** Spn Address */
    private String spnAddress;

    /** Manager Name */
    @Excel(name = "Manager Name")
    private String mangerName;

    /** Contact Email */
    private String contactEmail;

    /** Building Id */
    private Long buildingId;

    /** Manager Id */
    private Long userId;

    /** Task Name */
    private String taskName;

    /** PDF Path */
    private String pdfPath;

    /** SPN */
    private String spn;

    /** Company Name */
    private String companyName;

    /** Supplier Name */
    private String supplierName;

    /** Supplier Contact Number */
    private String supplierContactNumber;

    private String supplierEmail;

    private Long taskId;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public static String genWorkOrderNum(){
        String workOrderNumber = "WO-"+ DateUtils.parseDateToStr("yyyyMMdd",new Date());
        return workOrderNumber;

    }

    public void setWorkOrderId(Long workOrderId) 
    {
        this.workOrderId = workOrderId;
    }

    public Long getWorkOrderId() 
    {
        return workOrderId;
    }
    public void setWorkOrderNo(String workOrderNo) 
    {
        this.workOrderNo = workOrderNo;
    }

    public String getWorkOrderNo() 
    {
        return workOrderNo;
    }
    public void setDueBy(Date dueBy) 
    {
        this.dueBy = dueBy;
    }

    public Date getDueBy() 
    {
        return dueBy;
    }
    public void setPriority(String priority) 
    {
        this.priority = priority;
    }

    public String getPriority() 
    {
        return priority;
    }
    public void setAssignedTo(String assignedTo) 
    {
        this.assignedTo = assignedTo;
    }

    public String getAssignedTo() 
    {
        return assignedTo;
    }
    public void setServiceName(String serviceName) 
    {
        this.serviceName = serviceName;
    }

    public String getServiceName() 
    {
        return serviceName;
    }
    public void setServiceId(Long serviceId) 
    {
        this.serviceId = serviceId;
    }

    public Long getServiceId() 
    {
        return serviceId;
    }
    public void setContactNumber(String contactNumber) 
    {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() 
    {
        return contactNumber;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setMedia1(String media1) 
    {
        this.media1 = media1;
    }

    public String getMedia1() 
    {
        return media1;
    }
    public void setMedia2(String media2) 
    {
        this.media2 = media2;
    }

    public String getMedia2() 
    {
        return media2;
    }
    public void setSpnLogo(String spnLogo) 
    {
        this.spnLogo = spnLogo;
    }

    public String getSpnLogo() 
    {
        return spnLogo;
    }
    public void setSpnName(String spnName) 
    {
        this.spnName = spnName;
    }

    public String getSpnName() 
    {
        return spnName;
    }
    public void setSpnAddress(String spnAddress) 
    {
        this.spnAddress = spnAddress;
    }

    public String getSpnAddress() 
    {
        return spnAddress;
    }
    public void setMangerName(String mangerName) 
    {
        this.mangerName = mangerName;
    }

    public String getMangerName() 
    {
        return mangerName;
    }
    public void setContactEmail(String contactEmail) 
    {
        this.contactEmail = contactEmail;
    }

    public String getContactEmail() 
    {
        return contactEmail;
    }
    public void setBuildingId(Long buildingId) 
    {
        this.buildingId = buildingId;
    }

    public Long getBuildingId() 
    {
        return buildingId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }
    public void setPdfPath(String pdfPath) 
    {
        this.pdfPath = pdfPath;
    }

    public String getPdfPath() 
    {
        return pdfPath;
    }
    public void setSpn(String spn) 
    {
        this.spn = spn;
    }

    public String getSpn() 
    {
        return spn;
    }
    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
    }
    public void setSupplierName(String supplierName) 
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName() 
    {
        return supplierName;
    }
    public void setSupplierContactNumber(String supplierContactNumber) 
    {
        this.supplierContactNumber = supplierContactNumber;
    }

    public String getSupplierContactNumber() 
    {
        return supplierContactNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("workOrderId", getWorkOrderId())
            .append("workOrderNo", getWorkOrderNo())
            .append("dueBy", getDueBy())
            .append("priority", getPriority())
            .append("assignedTo", getAssignedTo())
            .append("serviceName", getServiceName())
            .append("serviceId", getServiceId())
            .append("contactNumber", getContactNumber())
            .append("description", getDescription())
            .append("media1", getMedia1())
            .append("media2", getMedia2())
            .append("spnLogo", getSpnLogo())
            .append("spnName", getSpnName())
            .append("spnAddress", getSpnAddress())
            .append("mangerName", getMangerName())
            .append("contactEmail", getContactEmail())
            .append("createTime", getCreateTime())
            .append("buildingId", getBuildingId())
            .append("userId", getUserId())
            .append("taskName", getTaskName())
            .append("pdfPath", getPdfPath())
            .append("spn", getSpn())
            .append("companyName", getCompanyName())
            .append("supplierName", getSupplierName())
            .append("supplierContactNumber", getSupplierContactNumber())
            .toString();
    }
}
