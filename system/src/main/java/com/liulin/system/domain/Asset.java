package com.liulin.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * Asset对象 asset
 * 
 * @author liulin
 * @date 2021-08-07
 */
public class Asset extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Asset Id */
    private Long assetId;

    /** Asset Name */
    @Excel(name = "Asset Name")
    private String assetName;

    /** Level */
    @Excel(name = "Level")
    private Long levelId;

    /** Brand */
    @Excel(name = "Brand")
    private String brand;

    /** Need Safty Check */
    @Excel(name = "Need Safty Check")
    private Integer needSaftyCheck;

    /** Can be booked */
    @Excel(name = "Can be booked")
    private String canBeBooked;

    /** Status */
    @Excel(name = "Status")
    private Integer status;

    /** Installed Date */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Installed Date", width = 30, dateFormat = "yyyy-MM-dd")
    private Date installedDate;

    /** Description */
    @Excel(name = "Description")
    private String description;

    /** Attachment */
    @Excel(name = "Attachment")
    private String attachmentIds;

    public void setAssetId(Long assetId) 
    {
        this.assetId = assetId;
    }

    public Long getAssetId() 
    {
        return assetId;
    }
    public void setAssetName(String assetName) 
    {
        this.assetName = assetName;
    }

    public String getAssetName() 
    {
        return assetName;
    }
    public void setLevelId(Long levelId) 
    {
        this.levelId = levelId;
    }

    public Long getLevelId() 
    {
        return levelId;
    }
    public void setBrand(String brand) 
    {
        this.brand = brand;
    }

    public String getBrand() 
    {
        return brand;
    }
    public void setNeedSaftyCheck(Integer needSaftyCheck) 
    {
        this.needSaftyCheck = needSaftyCheck;
    }

    public Integer getNeedSaftyCheck() 
    {
        return needSaftyCheck;
    }
    public void setCanBeBooked(String canBeBooked) 
    {
        this.canBeBooked = canBeBooked;
    }

    public String getCanBeBooked() 
    {
        return canBeBooked;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setInstalledDate(Date installedDate) 
    {
        this.installedDate = installedDate;
    }

    public Date getInstalledDate() 
    {
        return installedDate;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("assetId", getAssetId())
            .append("assetName", getAssetName())
            .append("levelId", getLevelId())
            .append("brand", getBrand())
            .append("needSaftyCheck", getNeedSaftyCheck())
            .append("canBeBooked", getCanBeBooked())
            .append("status", getStatus())
            .append("installedDate", getInstalledDate())
            .append("description", getDescription())
            .append("attachmentIds", getAttachmentIds())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
