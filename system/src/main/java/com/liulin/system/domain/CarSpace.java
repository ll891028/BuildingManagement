package com.liulin.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * CarSpace对象 car_space
 * 
 * @author liulin
 * @date 2021-08-17
 */
public class CarSpace extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Car Space Id */
    private Long carSpaceId;

    /** Car Space Number */
    @Excel(name = "Car Space Number")
    private String carSpaceNumber;

    /** Resident Id */
    @Excel(name = "Resident Id")
    private Long residentId;

    /** Building Id */
    @Excel(name = "Building Id")
    private Long buildingId;

    /** Car Plate Id */
    @Excel(name = "Car Plate Id")
    private Long carPlateId;

    private Integer order;

    private String carPlateNumber;

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setCarSpaceId(Long carSpaceId)
    {
        this.carSpaceId = carSpaceId;
    }

    public Long getCarSpaceId() 
    {
        return carSpaceId;
    }
    public void setCarSpaceNumber(String carSpaceNumber) 
    {
        this.carSpaceNumber = carSpaceNumber;
    }

    public String getCarSpaceNumber() 
    {
        return carSpaceNumber;
    }
    public void setResidentId(Long residentId) 
    {
        this.residentId = residentId;
    }

    public Long getResidentId() 
    {
        return residentId;
    }
    public void setBuildingId(Long buildingId) 
    {
        this.buildingId = buildingId;
    }

    public Long getBuildingId() 
    {
        return buildingId;
    }
    public void setCarPlateId(Long carPlateId) 
    {
        this.carPlateId = carPlateId;
    }

    public Long getCarPlateId() 
    {
        return carPlateId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("carSpaceId", getCarSpaceId())
            .append("carSpaceNumber", getCarSpaceNumber())
            .append("residentId", getResidentId())
            .append("buildingId", getBuildingId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("carPlateId", getCarPlateId())
            .toString();
    }
}
