package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.CarSpace;

/**
 * CarSpaceMapper接口
 * 
 * @author liulin
 * @date 2021-08-17
 */
public interface CarSpaceMapper 
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
     * 删除CarSpace
     * 
     * @param carSpaceId CarSpaceID
     * @return 结果
     */
    public int deleteCarSpaceById(Long carSpaceId);

    /**
     * 批量删除CarSpace
     * 
     * @param carSpaceIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCarSpaceByIds(String[] carSpaceIds);
}
