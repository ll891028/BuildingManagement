package com.liulin.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * building_level对象 building_level
 * 
 * @author liulin
 * @date 2021-08-04
 */
public class BuildingLevel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public BuildingLevel(String levelName,Long seq,Long buildingId){
        this.levelName = levelName;
        this.seq = seq;
        this.buildingId = buildingId;
    }

    public BuildingLevel(String levelName,Long seq){
        this.levelName = levelName;
        this.seq = seq;
    }


    public BuildingLevel(){

    }

    /** id */
    private Long levelId;

    /** building_id */
    @Excel(name = "building_id")
    private Long buildingId;

    /** level_name */
    @Excel(name = "level_name")
    private String levelName;

    /** order */
    @Excel(name = "order")
    private Long seq;

    public void setLevelId(Long levelId) 
    {
        this.levelId = levelId;
    }

    public Long getLevelId() 
    {
        return levelId;
    }
    public void setBuildingId(Long buildingId) 
    {
        this.buildingId = buildingId;
    }

    public Long getBuildingId() 
    {
        return buildingId;
    }
    public void setLevelName(String levelName) 
    {
        this.levelName = levelName;
    }

    public String getLevelName() 
    {
        return levelName;
    }
    public void setSeq(Long seq) 
    {
        this.seq = seq;
    }

    public Long getSeq() 
    {
        return seq;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("levelId", getLevelId())
            .append("buildingId", getBuildingId())
            .append("levelName", getLevelName())
            .append("seq", getSeq())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
