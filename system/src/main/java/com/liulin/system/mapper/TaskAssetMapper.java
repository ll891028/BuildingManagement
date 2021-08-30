package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.TaskAsset;

/**
 * TaskAssetMapper接口
 * 
 * @author liulin
 * @date 2021-08-20
 */
public interface TaskAssetMapper 
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
     * 删除TaskAsset
     * 
     * @param taskAssetId TaskAssetID
     * @return 结果
     */
    public int deleteTaskAssetById(Long taskAssetId);

    /**
     * 批量删除TaskAsset
     * 
     * @param taskAssetIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskAssetByIds(String[] taskAssetIds);

    public int deleteTaskAssetByTaskId(Long taskId);
}
