package com.liulin.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Defects Register对象 defects
 * 
 * @author liulin
 * @date 2021-10-15
 */
public class Defects extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Defect Id */
    private Long defectId;

    /** Date Raised */
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Excel(name = "Date Raised", width = 30, dateFormat = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateRaised;

    /** Location */
    @Excel(name = "Location")
    private String location;

    /** Defect Type */
    @Excel(name = "Defect Type")
    private String defectType;

    /** Description */
    @Excel(name = "Description")
    private String description;

    /** Status */
    @Excel(name = "Status")
    private Long status;

    /** Date Complete */
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Excel(name = "Date Complete", width = 30, dateFormat = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateComplete;

    /** Attachment */
    @Excel(name = "Attachment")
    private String attachmentIds;

    private Long buildingId;

    private Long companyId;

    private String defectNo;

    public String getDefectNo() {
        return defectNo;
    }

    public void setDefectNo(String defectNo) {
        this.defectNo = defectNo;
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

    public void setDefectId(Long defectId)
    {
        this.defectId = defectId;
    }

    public Long getDefectId() 
    {
        return defectId;
    }
    public void setDateRaised(Date dateRaised) 
    {
        this.dateRaised = dateRaised;
    }

    public Date getDateRaised() 
    {
        return dateRaised;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    public void setDefectType(String defectType) 
    {
        this.defectType = defectType;
    }

    public String getDefectType() 
    {
        return defectType;
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
    public void setDateComplete(Date dateComplete) 
    {
        this.dateComplete = dateComplete;
    }

    public Date getDateComplete() 
    {
        return dateComplete;
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
            .append("defectId", getDefectId())
            .append("dateRaised", getDateRaised())
            .append("location", getLocation())
            .append("defectType", getDefectType())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("dateComplete", getDateComplete())
            .append("attachmentIds", getAttachmentIds())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
