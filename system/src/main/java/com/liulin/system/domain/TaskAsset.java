package com.liulin.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * TaskAsset对象 task_asset
 * 
 * @author liulin
 * @date 2021-08-20
 */
public class TaskAsset extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Task Asset Id */
    private Long taskAssetId;

    /** Task Id */
    @Excel(name = "Task Id")
    private Long taskId;

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

    public void setTaskAssetId(Long taskAssetId)
    {
        this.taskAssetId = taskAssetId;
    }

    public Long getTaskAssetId() 
    {
        return taskAssetId;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
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
            .append("taskAssetId", getTaskAssetId())
            .append("taskId", getTaskId())
            .append("assetId", getAssetId())
            .toString();
    }
}
