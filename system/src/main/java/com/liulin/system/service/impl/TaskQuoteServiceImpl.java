package com.liulin.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.TaskQuoteMapper;
import com.liulin.system.domain.TaskQuote;
import com.liulin.system.service.ITaskQuoteService;
import com.liulin.common.core.text.Convert;

/**
 * TaskQuoteService业务层处理
 * 
 * @author liulin
 * @date 2021-08-20
 */
@Service
public class TaskQuoteServiceImpl implements ITaskQuoteService 
{
    @Autowired
    private TaskQuoteMapper taskQuoteMapper;

    /**
     * 查询TaskQuote
     * 
     * @param taskQuoteId TaskQuoteID
     * @return TaskQuote
     */
    @Override
    public TaskQuote selectTaskQuoteById(Long taskQuoteId)
    {
        return taskQuoteMapper.selectTaskQuoteById(taskQuoteId);
    }

    /**
     * 查询TaskQuote列表
     * 
     * @param taskQuote TaskQuote
     * @return TaskQuote
     */
    @Override
    public List<TaskQuote> selectTaskQuoteList(TaskQuote taskQuote)
    {
        return taskQuoteMapper.selectTaskQuoteList(taskQuote);
    }

    /**
     * 新增TaskQuote
     * 
     * @param taskQuote TaskQuote
     * @return 结果
     */
    @Override
    public int insertTaskQuote(TaskQuote taskQuote)
    {
        return taskQuoteMapper.insertTaskQuote(taskQuote);
    }

    /**
     * 修改TaskQuote
     * 
     * @param taskQuote TaskQuote
     * @return 结果
     */
    @Override
    public int updateTaskQuote(TaskQuote taskQuote)
    {
        return taskQuoteMapper.updateTaskQuote(taskQuote);
    }

    /**
     * 删除TaskQuote对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTaskQuoteByIds(String ids)
    {
        return taskQuoteMapper.deleteTaskQuoteByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除TaskQuote信息
     * 
     * @param taskQuoteId TaskQuoteID
     * @return 结果
     */
    @Override
    public int deleteTaskQuoteById(Long taskQuoteId)
    {
        return taskQuoteMapper.deleteTaskQuoteById(taskQuoteId);
    }

    @Override
    public int deleteTaskQuoteByTaskId(Long taskId) {
        return taskQuoteMapper.deleteTaskQuoteByTaskId(taskId);
    }
}
