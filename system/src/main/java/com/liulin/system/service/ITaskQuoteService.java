package com.liulin.system.service;

import java.util.List;
import com.liulin.system.domain.TaskQuote;

/**
 * TaskQuoteService接口
 * 
 * @author liulin
 * @date 2021-08-20
 */
public interface ITaskQuoteService 
{
    /**
     * 查询TaskQuote
     * 
     * @param taskQuoteId TaskQuoteID
     * @return TaskQuote
     */
    public TaskQuote selectTaskQuoteById(Long taskQuoteId);

    /**
     * 查询TaskQuote列表
     * 
     * @param taskQuote TaskQuote
     * @return TaskQuote集合
     */
    public List<TaskQuote> selectTaskQuoteList(TaskQuote taskQuote);

    /**
     * 新增TaskQuote
     * 
     * @param taskQuote TaskQuote
     * @return 结果
     */
    public int insertTaskQuote(TaskQuote taskQuote);

    /**
     * 修改TaskQuote
     * 
     * @param taskQuote TaskQuote
     * @return 结果
     */
    public int updateTaskQuote(TaskQuote taskQuote);

    /**
     * 批量删除TaskQuote
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskQuoteByIds(String ids);

    /**
     * 删除TaskQuote信息
     * 
     * @param taskQuoteId TaskQuoteID
     * @return 结果
     */
    public int deleteTaskQuoteById(Long taskQuoteId);

    public int deleteTaskQuoteByTaskId(Long taskId);
}
