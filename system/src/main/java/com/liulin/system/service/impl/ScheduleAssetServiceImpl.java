package com.liulin.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.ScheduleAssetMapper;
import com.liulin.system.domain.ScheduleAsset;
import com.liulin.system.service.IScheduleAssetService;
import com.liulin.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author liulin
 * @date 2021-09-13
 */
@Service
public class ScheduleAssetServiceImpl implements IScheduleAssetService 
{
    @Autowired
    private ScheduleAssetMapper scheduleAssetMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param schAssetId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public ScheduleAsset selectScheduleAssetById(Long schAssetId)
    {
        return scheduleAssetMapper.selectScheduleAssetById(schAssetId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param scheduleAsset 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ScheduleAsset> selectScheduleAssetList(ScheduleAsset scheduleAsset)
    {
        return scheduleAssetMapper.selectScheduleAssetList(scheduleAsset);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param scheduleAsset 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertScheduleAsset(ScheduleAsset scheduleAsset)
    {
        return scheduleAssetMapper.insertScheduleAsset(scheduleAsset);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param scheduleAsset 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateScheduleAsset(ScheduleAsset scheduleAsset)
    {
        return scheduleAssetMapper.updateScheduleAsset(scheduleAsset);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScheduleAssetByIds(String ids)
    {
        return scheduleAssetMapper.deleteScheduleAssetByIds(Convert.toStrArray(ids));
    }

    @Override
    public int deleteScheduleAssetBySchId(Long schId) {
        return scheduleAssetMapper.deleteScheduleAssetBySchId(schId);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param schAssetId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteScheduleAssetById(Long schAssetId)
    {
        return scheduleAssetMapper.deleteScheduleAssetById(schAssetId);
    }
}
