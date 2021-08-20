package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.TaskQuote;

/**
 * TaskQuoteMapper接口
 * 
 * @author liulin
 * @date 2021-08-20
 */
public interface TaskQuoteMapper 
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
     * 删除TaskQuote
     * 
     * @param taskQuoteId TaskQuoteID
     * @return 结果
     */
    public int deleteTaskQuoteById(Long taskQuoteId);

    /**
     * 批量删除TaskQuote
     * 
     * @param taskQuoteIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskQuoteByIds(String[] taskQuoteIds);
}
