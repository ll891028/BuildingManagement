package com.liulin.system.mapper;

import com.liulin.system.domain.BuildingLevel;
import com.liulin.system.domain.dto.AreaDto;

import java.util.List;

/**
 * building_levelMapper接口
 * 
 * @author liulin
 * @date 2021-08-04
 */
public interface BuildingLevelMapper 
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

    List<BuildingLevel> selectAreaNameByBuildingId(Long buildingId);

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
     * 删除building_level
     * 
     * @param levelId building_levelID
     * @return 结果
     */
    public int deleteBuildingLevelById(Long levelId);

    int deleteBuildingLevelByBuildingId(Long BuildingId);

    /**
     * 批量删除building_level
     * 
     * @param levelIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBuildingLevelByIds(String[] levelIds);

    Integer findMaxSeqByBuildingId(Long buildingId);

    List<AreaDto> selectAreaData(Long buildingId);
}
