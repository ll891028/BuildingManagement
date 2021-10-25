package com.liulin.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * Safety Check Asset对象 safety_check_asset
 * 
 * @author liulin
 * @date 2021-09-29
 */
public class SafetyCheckAsset extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Safety Check Asset Id */
    private Long safetyCheckAssetId;

    /** Safety Check Id */
    @Excel(name = "Safety Check Id")
    private Long safetyCheckId;

    /** Asset Id */
    @Excel(name = "Asset Id")
    private Long assetId;

    /** Note */
    @Excel(name = "Note")
    private String note;

    /** Attachment */
    @Excel(name = "Attachment")
    private String attachmentIds;

    private String assetName;

    private String levelName;

    private Integer inGoodCondition;

    private Long buildingId;

    private Long companyId;

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getInGoodCondition() {
        return inGoodCondition;
    }

    public void setInGoodCondition(Integer inGoodCondition) {
        this.inGoodCondition = inGoodCondition;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public void setSafetyCheckAssetId(Long safetyCheckAssetId)
    {
        this.safetyCheckAssetId = safetyCheckAssetId;
    }

    public Long getSafetyCheckAssetId() 
    {
        return safetyCheckAssetId;
    }
    public void setSafetyCheckId(Long safetyCheckId) 
    {
        this.safetyCheckId = safetyCheckId;
    }

    public Long getSafetyCheckId() 
    {
        return safetyCheckId;
    }
    public void setAssetId(Long assetId) 
    {
        this.assetId = assetId;
    }

    public Long getAssetId() 
    {
        return assetId;
    }
    public void setNote(String note) 
    {
        this.note = note;
    }

    public String getNote() 
    {
        return note;
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
            .append("safetyCheckAssetId", getSafetyCheckAssetId())
            .append("safetyCheckId", getSafetyCheckId())
            .append("assetId", getAssetId())
            .append("note", getNote())
            .append("attachmentIds", getAttachmentIds())
            .toString();
    }
}
