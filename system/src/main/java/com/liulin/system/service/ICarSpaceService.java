package com.liulin.system.service;

import java.util.List;
import com.liulin.system.domain.CarSpace;

/**
 * CarSpaceService接口
 * 
 * @author liulin
 * @date 2021-08-17
 */
public interface ICarSpaceService 
{
    /**
     * 查询CarSpace
     * 
     * @param carSpaceId CarSpaceID
     * @return CarSpace
     */
    public CarSpace selectCarSpaceById(Long carSpaceId);

    /**
     * 查询CarSpace列表
     * 
     * @param carSpace CarSpace
     * @return CarSpace集合
     */
    public List<CarSpace> selectCarSpaceList(CarSpace carSpace);

    /**
     * 新增CarSpace
     * 
     * @param carSpace CarSpace
     * @return 结果
     */
    public int insertCarSpace(CarSpace carSpace);

    /**
     * 修改CarSpace
     * 
     * @param carSpace CarSpace
     * @return 结果
     */
    public int updateCarSpace(CarSpace carSpace);

    /**
     * 批量删除CarSpace
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCarSpaceByIds(String ids);

    /**
     * 删除CarSpace信息
     * 
     * @param carSpaceId CarSpaceID
     * @return 结果
     */
    public int deleteCarSpaceById(Long carSpaceId);
}
