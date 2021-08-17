package com.liulin.system.service.impl;

import java.util.List;
import com.liulin.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.CarPlateMapper;
import com.liulin.system.domain.CarPlate;
import com.liulin.system.service.ICarPlateService;
import com.liulin.common.core.text.Convert;

/**
 * PlateService业务层处理
 * 
 * @author liulin
 * @date 2021-08-17
 */
@Service
public class CarPlateServiceImpl implements ICarPlateService 
{
    @Autowired
    private CarPlateMapper carPlateMapper;

    /**
     * 查询Plate
     * 
     * @param carPlateId PlateID
     * @return Plate
     */
    @Override
    public CarPlate selectCarPlateById(Long carPlateId)
    {
        return carPlateMapper.selectCarPlateById(carPlateId);
    }

    /**
     * 查询Plate列表
     * 
     * @param carPlate Plate
     * @return Plate
     */
    @Override
    public List<CarPlate> selectCarPlateList(CarPlate carPlate)
    {
        return carPlateMapper.selectCarPlateList(carPlate);
    }

    /**
     * 新增Plate
     * 
     * @param carPlate Plate
     * @return 结果
     */
    @Override
    public int insertCarPlate(CarPlate carPlate)
    {
        carPlate.setCreateTime(DateUtils.getNowDate());
        return carPlateMapper.insertCarPlate(carPlate);
    }

    /**
     * 修改Plate
     * 
     * @param carPlate Plate
     * @return 结果
     */
    @Override
    public int updateCarPlate(CarPlate carPlate)
    {
        carPlate.setUpdateTime(DateUtils.getNowDate());
        return carPlateMapper.updateCarPlate(carPlate);
    }

    /**
     * 删除Plate对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCarPlateByIds(String ids)
    {
        return carPlateMapper.deleteCarPlateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除Plate信息
     * 
     * @param carPlateId PlateID
     * @return 结果
     */
    @Override
    public int deleteCarPlateById(Long carPlateId)
    {
        return carPlateMapper.deleteCarPlateById(carPlateId);
    }
}
