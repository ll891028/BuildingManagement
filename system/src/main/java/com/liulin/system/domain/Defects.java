package com.liulin.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Date Raised", width = 30, dateFormat = "yyyy-MM-dd")
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Date Complete", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dateComplete;

    /** Attachment */
    @Excel(name = "Attachment")
    private String attachmentIds;

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
