package com.liulin.system.service;

import java.util.List;
import com.liulin.system.domain.BuildingLevel;

/**
 * building_levelService接口
 * 
 * @author liulin
 * @date 2021-08-04
 */
public interface IBuildingLevelService 
{
    /**
     * 查询building_level
     * 
     * @param levelId building_levelID
     * @return building_level
     */
    public BuildingLevel selectBuildingLevelById(Long levelId);

    /**
     * 查询building_level列表
     * 
     * @param buildingLevel building_level
     * @return building_level集合
     */
    public List<BuildingLevel> selectBuildingLevelList(BuildingLevel buildingLevel);

    /**
     * 新增building_level
     * 
     * @param buildingLevel building_level
     * @return 结果
     */
    public int insertBuildingLevel(BuildingLevel buildingLevel);

    /**
     * 修改building_level
     * 
     * @param buildingLevel building_level
     * @return 结果
     */
    public int updateBuildingLevel(BuildingLevel buildingLevel);

    /**
     * 批量删除building_level
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBuildingLevelByIds(String ids);

    /**
     * 删除building_level信息
     * 
     * @param levelId building_levelID
     * @return 结果
     */
    public int deleteBuildingLevelById(Long levelId);

    void generateDefaultLevel(Long buildingId,Integer levels, Integer ifGroundFloor, Integer basements,String createBy);

    void deleteByBuildingId(Long buildingId);
}
