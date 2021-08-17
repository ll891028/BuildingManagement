package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.CarPlate;

/**
 * PlateMapper接口
 * 
 * @author liulin
 * @date 2021-08-17
 */
public interface CarPlateMapper 
{
    /**
     * 查询Plate
     * 
     * @param carPlateId PlateID
     * @return Plate
     */
    public CarPlate selectCarPlateById(Long carPlateId);

    /**
     * 查询Plate列表
     * 
     * @param carPlate Plate
     * @return Plate集合
     */
    public List<CarPlate> selectCarPlateList(CarPlate carPlate);

    /**
     * 新增Plate
     * 
     * @param carPlate Plate
     * @return 结果
     */
    public int insertCarPlate(CarPlate carPlate);

    /**
     * 修改Plate
     * 
     * @param carPlate Plate
     * @return 结果
     */
    public int updateCarPlate(CarPlate carPlate);

    /**
     * 删除Plate
     * 
     * @param carPlateId PlateID
     * @return 结果
     */
    public int deleteCarPlateById(Long carPlateId);

    /**
     * 批量删除Plate
     * 
     * @param carPlateIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCarPlateByIds(String[] carPlateIds);
}
