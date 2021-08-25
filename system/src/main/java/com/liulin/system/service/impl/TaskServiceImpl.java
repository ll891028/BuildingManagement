package com.liulin.system.service.impl;

import java.util.List;

import com.liulin.common.constant.Constants;
import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.system.domain.TaskAsset;
import com.liulin.system.domain.TaskQuote;
import com.liulin.system.service.ITaskAssetService;
import com.liulin.system.service.ITaskQuoteService;
import org.apache.commons.collections.CollectionUtils;
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

    @Autowired
    private ITaskQuoteService taskQuoteService;

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
        if(CollectionUtils.isNotEmpty(task.getAssetIds())){
            for (Long assetId : task.getAssetIds()) {
                TaskAsset taskAsset = new TaskAsset();
                taskAsset.setTaskId(task.getTaskId());
                taskAsset.setAssetId(assetId);
                taskAssetService.insertTaskAsset(taskAsset);
            }

        }
        if(task.getNeedWorkOrder().equals(Task.N)){
            task.setOrderInstruction(null);
            task.setOrderSupplierId(null);
            task.setOrderStatus(null);
        }
        if(task.getNeedQuote().equals(Task.N)){
            task.setQuoteInstruction(null);
            task.setQuoteStatus(null);
            task.setQuoteSupplierIds(null);
        }
        if(CollectionUtils.isNotEmpty(task.getQuoteSupplierIds())){
            for (Long supplierId : task.getQuoteSupplierIds()) {
                TaskQuote saver = new TaskQuote();
                saver.setTaskId(task.getTaskId());
                saver.setSupplierId(supplierId);
                taskQuoteService.insertTaskQuote(saver);
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
