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
 * Safety Check对象 safety_check
 * 
 * @author liulin
 * @date 2021-09-29
 */
public class SafetyCheck extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Safety Check Id */
    private Long safetyCheckId;

    /** Frequency */
    @Excel(name = "Frequency")
    private Integer frequency;

    /** Date Schedule */
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Excel(name = "Date Schedule", width = 30, dateFormat = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateSchedule;

    /** Date Complete */
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Excel(name = "Date Complete", width = 30, dateFormat = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateComplete;

    /** Status */
    @Excel(name = "Status")
    private Integer status;

    private Long buildingId;

    private List<Long> assetIds;

    public List<Long> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<Long> assetIds) {
        this.assetIds = assetIds;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public void setSafetyCheckId(Long safetyCheckId)
    {
        this.safetyCheckId = safetyCheckId;
    }

    public Long getSafetyCheckId() 
    {
        return safetyCheckId;
    }
    public void setFrequency(Integer frequency) 
    {
        this.frequency = frequency;
    }

    public Integer getFrequency() 
    {
        return frequency;
    }
    public void setDateSchedule(Date dateSchedule) 
    {
        this.dateSchedule = dateSchedule;
    }

    public Date getDateSchedule() 
    {
        return dateSchedule;
    }
    public void setDateComplete(Date dateComplete) 
    {
        this.dateComplete = dateComplete;
    }

    public Date getDateComplete() 
    {
        return dateComplete;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("safetyCheckId", getSafetyCheckId())
            .append("frequency", getFrequency())
            .append("dateSchedule", getDateSchedule())
            .append("dateComplete", getDateComplete())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
