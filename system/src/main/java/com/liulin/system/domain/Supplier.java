package com.liulin.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * supplier对象 supplier
 * 
 * @author liulin
 * @date 2021-08-09
 */
public class Supplier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Supplier Id */
    private Long supplierId;

    /** Company Name */
    @Excel(name = "Company Name")
    private String companyName;

    /** Service */
    @Excel(name = "Service")
    private Long serviceId;

    /** Contact Number */
    @Excel(name = "Contact Number")
    private String contactNumber;

    /** Email */
    @Excel(name = "Email")
    private String email;

    /** Status */
    @Excel(name = "Status")
    private Integer status;

    /** License Number */
    @Excel(name = "License Number")
    private String licenseNumber;

    /** Insurance Expired Date */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Insurance Expired Date", width = 30, dateFormat = "yyyy-MM-dd")
    private Date insuraceExpiredDate;

    /** Description */
    private String description;

    /** Attachment */
    @Excel(name = "Attachment")
    private String attachmentIds;

    /** Contact Person */
    private String contactPerson;

    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }
    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
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
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setLicenseNumber(String licenseNumber) 
    {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseNumber() 
    {
        return licenseNumber;
    }
    public void setInsuraceExpiredDate(Date insuraceExpiredDate) 
    {
        this.insuraceExpiredDate = insuraceExpiredDate;
    }

    public Date getInsuraceExpiredDate() 
    {
        return insuraceExpiredDate;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setAttachmentIds(String attachmentIds) 
    {
        this.attachmentIds = attachmentIds;
    }

    public String getAttachmentIds() 
    {
        return attachmentIds;
    }
    public void setContactPerson(String contactPerson) 
    {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() 
    {
        return contactPerson;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("supplierId", getSupplierId())
            .append("companyName", getCompanyName())
            .append("serviceId", getServiceId())
            .append("contactNumber", getContactNumber())
            .append("email", getEmail())
            .append("status", getStatus())
            .append("licenseNumber", getLicenseNumber())
            .append("insuraceExpiredDate", getInsuraceExpiredDate())
            .append("description", getDescription())
            .append("attachmentIds", getAttachmentIds())
            .append("contactPerson", getContactPerson())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
