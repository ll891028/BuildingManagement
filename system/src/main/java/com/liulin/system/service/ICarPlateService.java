package com.liulin.system.service;

import java.util.List;
import com.liulin.system.domain.CarPlate;

/**
 * PlateService接口
 * 
 * @author liulin
 * @date 2021-08-17
 */
public interface ICarPlateService 
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
     * 批量删除Plate
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCarPlateByIds(String ids);

    /**
     * 删除Plate信息
     * 
     * @param carPlateId PlateID
     * @return 结果
     */
    public int deleteCarPlateById(Long carPlateId);

    int deleteCarPlateByResidentId(Long residentId);

    String checkCarPlateNumberUnique(CarPlate carPlate);
}
