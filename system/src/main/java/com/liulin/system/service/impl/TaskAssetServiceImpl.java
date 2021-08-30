package com.liulin.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.TaskAssetMapper;
import com.liulin.system.domain.TaskAsset;
import com.liulin.system.service.ITaskAssetService;
import com.liulin.common.core.text.Convert;

/**
 * TaskAssetService业务层处理
 * 
 * @author liulin
 * @date 2021-08-20
 */
@Service
public class TaskAssetServiceImpl implements ITaskAssetService 
{
    @Autowired
    private TaskAssetMapper taskAssetMapper;

    /**
     * 查询TaskAsset
     * 
     * @param taskAssetId TaskAssetID
     * @return TaskAsset
     */
    @Override
    public TaskAsset selectTaskAssetById(Long taskAssetId)
    {
        return taskAssetMapper.selectTaskAssetById(taskAssetId);
    }

    /**
     * 查询TaskAsset列表
     * 
     * @param taskAsset TaskAsset
     * @return TaskAsset
     */
    @Override
    public List<TaskAsset> selectTaskAssetList(TaskAsset taskAsset)
    {
        return taskAssetMapper.selectTaskAssetList(taskAsset);
    }

    /**
     * 新增TaskAsset
     * 
     * @param taskAsset TaskAsset
     * @return 结果
     */
    @Override
    public int insertTaskAsset(TaskAsset taskAsset)
    {
        return taskAssetMapper.insertTaskAsset(taskAsset);
    }

    /**
     * 修改TaskAsset
     * 
     * @param taskAsset TaskAsset
     * @return 结果
     */
    @Override
    public int updateTaskAsset(TaskAsset taskAsset)
    {
        return taskAssetMapper.updateTaskAsset(taskAsset);
    }

    /**
     * 删除TaskAsset对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTaskAssetByIds(String ids)
    {
        return taskAssetMapper.deleteTaskAssetByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除TaskAsset信息
     * 
     * @param taskAssetId TaskAssetID
     * @return 结果
     */
    @Override
    public int deleteTaskAssetById(Long taskAssetId)
    {
        return taskAssetMapper.deleteTaskAssetById(taskAssetId);
    }

    @Override
    public int deleteTaskAssetByTaskId(Long taskId) {
        return taskAssetMapper.deleteTaskAssetByTaskId(taskId);
    }
}
