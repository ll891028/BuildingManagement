package com.liulin.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 schedule_asset
 * 
 * @author liulin
 * @date 2021-09-13
 */
public class ScheduleAsset extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Task Asset Id */
    private Long schAssetId;

    /** Task Id */
    @Excel(name = "Task Id")
    private Long schId;

    /** Asset Id */
    @Excel(name = "Asset Id")
    private Long assetId;

    private String assetName;

    private String levelName;

    private Integer status;

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setSchAssetId(Long schAssetId)
    {
        this.schAssetId = schAssetId;
    }

    public Long getSchAssetId() 
    {
        return schAssetId;
    }
    public void setSchId(Long schId) 
    {
        this.schId = schId;
    }

    public Long getSchId() 
    {
        return schId;
    }
    public void setAssetId(Long assetId) 
    {
        this.assetId = assetId;
    }

    public Long getAssetId() 
    {
        return assetId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("schAssetId", getSchAssetId())
            .append("schId", getSchId())
            .append("assetId", getAssetId())
            .toString();
    }
}
