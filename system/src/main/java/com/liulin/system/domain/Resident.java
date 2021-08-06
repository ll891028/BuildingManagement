package com.liulin.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * resident对象 resident
 * 
 * @author liulin
 * @date 2021-08-06
 */
public class Resident extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** residentId */
    private Long residentId;

    /** First Name */
    @Excel(name = "First Name")
    private String firstName;

    /** Last Name */
    @Excel(name = "Last Name")
    private String lastName;

    /** Contact Number */
    @Excel(name = "Contact Number")
    private String contactNumber;

    /** Email */
    @Excel(name = "Email")
    private String email;

    /** description */
    @Excel(name = "description")
    private String description;

    /** Status */
    @Excel(name = "Status")
    private Long status;

    /** Current Resident */
    @Excel(name = "Current Resident")
    private Long currentResident;

    /** Unit Number */
    @Excel(name = "Unit Number")
    private String unitNumber;

    /** Level */
    @Excel(name = "Level")
    private Long levelId;

    /** Committee Member */
    @Excel(name = "Committee Member")
    private Integer committeeMember;

    /** Carpark Space Amount */
    @Excel(name = "Carpark Space Amount")
    private Integer carparkSpaceAmount;

    /** Resident Type */
    @Excel(name = "Resident Type")
    private Integer residentType;

    /** Attachments */
    @Excel(name = "Attachments")
    private String attachmentIds;

    /** User Id */
    private Long userId;

    public void setResidentId(Long residentId) 
    {
        this.residentId = residentId;
    }

    public Long getResidentId() 
    {
        return residentId;
    }
    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }

    public String getFirstName() 
    {
        return firstName;
    }
    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

    public String getLastName() 
    {
        return lastName;
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
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setCurrentResident(Long currentResident) 
    {
        this.currentResident = currentResident;
    }

    public Long getCurrentResident() 
    {
        return currentResident;
    }
    public void setUnitNumber(String unitNumber) 
    {
        this.unitNumber = unitNumber;
    }

    public String getUnitNumber() 
    {
        return unitNumber;
    }
    public void setLevelId(Long levelId) 
    {
        this.levelId = levelId;
    }

    public Long getLevelId() 
    {
        return levelId;
    }
    public void setCommitteeMember(Integer committeeMember) 
    {
        this.committeeMember = committeeMember;
    }

    public Integer getCommitteeMember() 
    {
        return committeeMember;
    }
    public void setCarparkSpaceAmount(Integer carparkSpaceAmount) 
    {
        this.carparkSpaceAmount = carparkSpaceAmount;
    }

    public Integer getCarparkSpaceAmount() 
    {
        return carparkSpaceAmount;
    }
    public void setResidentType(Integer residentType) 
    {
        this.residentType = residentType;
    }

    public Integer getResidentType() 
    {
        return residentType;
    }
    public void setAttachmentIds(String attachmentIds) 
    {
        this.attachmentIds = attachmentIds;
    }

    public String getAttachmentIds() 
    {
        return attachmentIds;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("residentId", getResidentId())
            .append("firstName", getFirstName())
            .append("lastName", getLastName())
            .append("contactNumber", getContactNumber())
            .append("email", getEmail())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("currentResident", getCurrentResident())
            .append("unitNumber", getUnitNumber())
            .append("levelId", getLevelId())
            .append("committeeMember", getCommitteeMember())
            .append("carparkSpaceAmount", getCarparkSpaceAmount())
            .append("residentType", getResidentType())
            .append("attachmentIds", getAttachmentIds())
            .append("userId", getUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
