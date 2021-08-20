package com.liulin.system.service.impl;

import java.util.List;
import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.system.domain.TaskAsset;
import com.liulin.system.service.ITaskAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.TaskMapper;
import com.liulin.system.domain.Task;
import com.liulin.system.service.ITaskService;
import com.liulin.common.core.text.Convert;

/**
 * TaskService业务层处理
 * 
 * @author liulin
 * @date 2021-08-19
 */
@Service
public class TaskServiceImpl implements ITaskService 
{
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ITaskAssetService taskAssetService;

    /**
     * 查询Task
     * 
     * @param taskId TaskID
     * @return Task
     */
    @Override
    public Task selectTaskById(Long taskId)
    {
        return taskMapper.selectTaskById(taskId);
    }

    /**
     * 查询Task列表
     * 
     * @param task Task
     * @return Task
     */
    @Override
    public List<Task> selectTaskList(Task task)
    {
        return taskMapper.selectTaskList(task);
    }

    /**
     * 新增Task
     * 
     * @param task Task
     * @return 结果
     */
    @Override
    public int insertTask(Task task)
    {
        task.setCreateTime(DateUtils.getNowDate());
        int result = taskMapper.insertTask(task);
        if(StringUtils.isNotEmpty(task.getAssetIds())){
            for (String assetIdStr : task.getAssetIds().split(",")) {
                TaskAsset taskAsset = new TaskAsset();
                taskAsset.setTaskId(task.getTaskId());
                taskAsset.setAssetId(Long.valueOf(assetIdStr));
                taskAssetService.insertTaskAsset(taskAsset);
            }

        }
        return result;
    }

    /**
     * 修改Task
     * 
     * @param task Task
     * @return 结果
     */
    @Override
    public int updateTask(Task task)
    {
        task.setUpdateTime(DateUtils.getNowDate());
        return taskMapper.updateTask(task);
    }

    /**
     * 删除Task对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTaskByIds(String ids)
    {
        return taskMapper.deleteTaskByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除Task信息
     * 
     * @param taskId TaskID
     * @return 结果
     */
    @Override
    public int deleteTaskById(Long taskId)
    {
        return taskMapper.deleteTaskById(taskId);
    }
}
