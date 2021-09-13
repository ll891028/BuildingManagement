package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.ScheduleQuote;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author liulin
 * @date 2021-09-13
 */
public interface ScheduleQuoteMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param scheduleQuoteId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public ScheduleQuote selectScheduleQuoteById(Long scheduleQuoteId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param scheduleQuote 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ScheduleQuote> selectScheduleQuoteList(ScheduleQuote scheduleQuote);

    /**
     * 新增【请填写功能名称】
     * 
     * @param scheduleQuote 【请填写功能名称】
     * @return 结果
     */
    public int insertScheduleQuote(ScheduleQuote scheduleQuote);

    /**
     * 修改【请填写功能名称】
     * 
     * @param scheduleQuote 【请填写功能名称】
     * @return 结果
     */
    public int updateScheduleQuote(ScheduleQuote scheduleQuote);

    /**
     * 删除【请填写功能名称】
     * 
     * @param scheduleQuoteId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteScheduleQuoteById(Long scheduleQuoteId);

    public int deleteScheduleQuoteBySchId(Long schId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param scheduleQuoteIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteScheduleQuoteByIds(String[] scheduleQuoteIds);
}
