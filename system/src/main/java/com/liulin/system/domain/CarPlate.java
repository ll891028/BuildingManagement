package com.liulin.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * Plate对象 car_plate
 * 
 * @author liulin
 * @date 2021-08-17
 */
public class CarPlate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Car Plate Id */
    private Long carPlateId;

    /** Car Plate Number */
    @Excel(name = "Car Plate Number")
    private String carPlateNumber;

    /** Resident Id */
    @Excel(name = "Resident Id")
    private Long residentId;

    /** Building Id */
    @Excel(name = "Building Id")
    private Long buildingId;

    public void setCarPlateId(Long carPlateId) 
    {
        this.carPlateId = carPlateId;
    }

    public Long getCarPlateId() 
    {
        return carPlateId;
    }
    public void setCarPlateNumber(String carPlateNumber) 
    {
        this.carPlateNumber = carPlateNumber;
    }

    public String getCarPlateNumber() 
    {
        return carPlateNumber;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("carPlateId", getCarPlateId())
            .append("carPlateNumber", getCarPlateNumber())
            .append("residentId", getResidentId())
            .append("buildingId", getBuildingId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
