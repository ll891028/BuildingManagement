package com.liulin.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.BuildingLevelMapper;
import com.liulin.system.domain.BuildingLevel;
import com.liulin.system.service.IBuildingLevelService;
import com.liulin.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

/**
 * building_levelService业务层处理
 *
 * @author liulin
 * @date 2021-08-04
 */
@Service
public class BuildingLevelServiceImpl implements IBuildingLevelService {
    @Autowired
    private BuildingLevelMapper buildingLevelMapper;

    /**
     * 查询building_level
     *
     * @param levelId building_levelID
     * @return building_level
     */
    @Override
    public BuildingLevel selectBuildingLevelById(Long levelId) {
        return buildingLevelMapper.selectBuildingLevelById(levelId);
    }

    /**
     * 查询building_level列表
     *
     * @param buildingLevel building_level
     * @return building_level
     */
    @Override
    public List<BuildingLevel> selectBuildingLevelList(BuildingLevel buildingLevel) {
        return buildingLevelMapper.selectBuildingLevelList(buildingLevel);
    }

    @Override
    public List<BuildingLevel> selectBuildingLevelListByBuildingId(Long buildingId) {
        BuildingLevel query = new BuildingLevel();
        query.setBuildingId(buildingId);
        return buildingLevelMapper.selectBuildingLevelList(query);
    }

    /**
     * 新增building_level
     *
     * @param buildingLevel building_level
     * @return 结果
     */
    @Override
    public int insertBuildingLevel(BuildingLevel buildingLevel) {
        buildingLevel.setCreateTime(DateUtils.getNowDate());
        Integer maxSeqByBuildingId = buildingLevelMapper.findMaxSeqByBuildingId(buildingLevel.getBuildingId());
        buildingLevel.setSeq(maxSeqByBuildingId.longValue()+1L);
        return buildingLevelMapper.insertBuildingLevel(buildingLevel);
    }

    /**
     * 修改building_level
     *
     * @param buildingLevel building_level
     * @return 结果
     */
    @Override
    public int updateBuildingLevel(BuildingLevel buildingLevel) {
        buildingLevel.setUpdateTime(DateUtils.getNowDate());
        return buildingLevelMapper.updateBuildingLevel(buildingLevel);
    }

    /**
     * 删除building_level对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBuildingLevelByIds(String ids) {
        return buildingLevelMapper.deleteBuildingLevelByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除building_level信息
     *
     * @param levelId building_levelID
     * @return 结果
     */
    @Override
    public int deleteBuildingLevelById(Long levelId) {
        return buildingLevelMapper.deleteBuildingLevelById(levelId);
    }

    @Override
    @Transactional
    public void generateDefaultLevel(Long buildingId, Integer levels, Integer ifGroundFloor, Integer basements,
                                     String createBy) {
        //清空对应building下的所有level数据
        deleteByBuildingId(buildingId);
        Long levelCount = Long.valueOf(String.valueOf(levels));
//        List<BuildingLevel> saver = new ArrayList<>();
        if (levels != null && levels != 0) {
            for (Integer i = levels; i > 0; i--) {
                BuildingLevel saver =
                        new BuildingLevel("Level " + (i),levelCount--, buildingId);
                saver.setCreateBy(createBy);
                buildingLevelMapper.insertBuildingLevel(saver);
            }
        }
        if (ifGroundFloor != null && ifGroundFloor == 1) {
            //有groundFloor
            BuildingLevel saver = new BuildingLevel("Ground Floor", levelCount, buildingId);
            saver.setCreateBy(createBy);
            buildingLevelMapper.insertBuildingLevel(saver);
            levelCount++;
        }
        Long basementsCount = 0L;
        if (basements != null && basements != 0) {
            for (Integer i = 0; i < basements; i++) {
                BuildingLevel saver = new BuildingLevel("Basement " + (i + 1), --basementsCount, buildingId);
                saver.setCreateBy(createBy);
                buildingLevelMapper.insertBuildingLevel(saver);
            }
        }
//        throw new RuntimeException();

    }

    @Override
    public void deleteByBuildingId(Long buildingId) {
        buildingLevelMapper.deleteBuildingLevelByBuildingId(buildingId);
    }
}
