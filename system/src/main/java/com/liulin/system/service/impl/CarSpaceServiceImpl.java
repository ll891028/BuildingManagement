package com.liulin.system.service.impl;

import java.util.List;
import com.liulin.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.CarSpaceMapper;
import com.liulin.system.domain.CarSpace;
import com.liulin.system.service.ICarSpaceService;
import com.liulin.common.core.text.Convert;

/**
 * CarSpaceService业务层处理
 * 
 * @author liulin
 * @date 2021-08-17
 */
@Service
public class CarSpaceServiceImpl implements ICarSpaceService 
{
    @Autowired
    private CarSpaceMapper carSpaceMapper;

    /**
     * 查询CarSpace
     * 
     * @param carSpaceId CarSpaceID
     * @return CarSpace
     */
    @Override
    public CarSpace selectCarSpaceById(Long carSpaceId)
    {
        return carSpaceMapper.selectCarSpaceById(carSpaceId);
    }

    /**
     * 查询CarSpace列表
     * 
     * @param carSpace CarSpace
     * @return CarSpace
     */
    @Override
    public List<CarSpace> selectCarSpaceList(CarSpace carSpace)
    {
        return carSpaceMapper.selectCarSpaceList(carSpace);
    }

    /**
     * 新增CarSpace
     * 
     * @param carSpace CarSpace
     * @return 结果
     */
    @Override
    public int insertCarSpace(CarSpace carSpace)
    {
        carSpace.setCreateTime(DateUtils.getNowDate());
        return carSpaceMapper.insertCarSpace(carSpace);
    }

    /**
     * 修改CarSpace
     * 
     * @param carSpace CarSpace
     * @return 结果
     */
    @Override
    public int updateCarSpace(CarSpace carSpace)
    {
        carSpace.setUpdateTime(DateUtils.getNowDate());
        return carSpaceMapper.updateCarSpace(carSpace);
    }

    /**
     * 删除CarSpace对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCarSpaceByIds(String ids)
    {
        return carSpaceMapper.deleteCarSpaceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除CarSpace信息
     * 
     * @param carSpaceId CarSpaceID
     * @return 结果
     */
    @Override
    public int deleteCarSpaceById(Long carSpaceId)
    {
        return carSpaceMapper.deleteCarSpaceById(carSpaceId);
    }
}
