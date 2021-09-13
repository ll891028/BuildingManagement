package com.liulin.system.service;

import java.util.List;
import com.liulin.system.domain.ScheduleAsset;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author liulin
 * @date 2021-09-13
 */
public interface IScheduleAssetService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param schAssetId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public ScheduleAsset selectScheduleAssetById(Long schAssetId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param scheduleAsset 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ScheduleAsset> selectScheduleAssetList(ScheduleAsset scheduleAsset);

    /**
     * 新增【请填写功能名称】
     * 
     * @param scheduleAsset 【请填写功能名称】
     * @return 结果
     */
    public int insertScheduleAsset(ScheduleAsset scheduleAsset);

    /**
     * 修改【请填写功能名称】
     * 
     * @param scheduleAsset 【请填写功能名称】
     * @return 结果
     */
    public int updateScheduleAsset(ScheduleAsset scheduleAsset);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScheduleAssetByIds(String ids);

    public int deleteScheduleAssetBySchId(Long schId);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param schAssetId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteScheduleAssetById(Long schAssetId);
}
