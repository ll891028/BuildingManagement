package com.liulin.system.service;

import java.util.List;
import com.liulin.system.domain.TaskAsset;

/**
 * TaskAssetService接口
 * 
 * @author liulin
 * @date 2021-08-20
 */
public interface ITaskAssetService 
{
    /**
     * 查询TaskAsset
     * 
     * @param taskAssetId TaskAssetID
     * @return TaskAsset
     */
    public TaskAsset selectTaskAssetById(Long taskAssetId);

    /**
     * 查询TaskAsset列表
     * 
     * @param taskAsset TaskAsset
     * @return TaskAsset集合
     */
    public List<TaskAsset> selectTaskAssetList(TaskAsset taskAsset);

    /**
     * 新增TaskAsset
     * 
     * @param taskAsset TaskAsset
     * @return 结果
     */
    public int insertTaskAsset(TaskAsset taskAsset);

    /**
     * 修改TaskAsset
     * 
     * @param taskAsset TaskAsset
     * @return 结果
     */
    public int updateTaskAsset(TaskAsset taskAsset);

    /**
     * 批量删除TaskAsset
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskAssetByIds(String ids);

    /**
     * 删除TaskAsset信息
     * 
     * @param taskAssetId TaskAssetID
     * @return 结果
     */
    public int deleteTaskAssetById(Long taskAssetId);

    public int deleteTaskAssetByTaskId(Long taskId);
}
