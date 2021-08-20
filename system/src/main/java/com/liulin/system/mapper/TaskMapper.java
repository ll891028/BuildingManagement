package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.Task;

/**
 * TaskMapper接口
 * 
 * @author liulin
 * @date 2021-08-19
 */
public interface TaskMapper 
{
    /**
     * 查询Task
     * 
     * @param taskId TaskID
     * @return Task
     */
    public Task selectTaskById(Long taskId);

    /**
     * 查询Task列表
     * 
     * @param task Task
     * @return Task集合
     */
    public List<Task> selectTaskList(Task task);

    /**
     * 新增Task
     * 
     * @param task Task
     * @return 结果
     */
    public int insertTask(Task task);

    /**
     * 修改Task
     * 
     * @param task Task
     * @return 结果
     */
    public int updateTask(Task task);

    /**
     * 删除Task
     * 
     * @param taskId TaskID
     * @return 结果
     */
    public int deleteTaskById(Long taskId);

    /**
     * 批量删除Task
     * 
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskByIds(String[] taskIds);
}
