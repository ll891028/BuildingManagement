package com.liulin.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.ScheduleQuoteMapper;
import com.liulin.system.domain.ScheduleQuote;
import com.liulin.system.service.IScheduleQuoteService;
import com.liulin.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author liulin
 * @date 2021-09-13
 */
@Service
public class ScheduleQuoteServiceImpl implements IScheduleQuoteService 
{
    @Autowired
    private ScheduleQuoteMapper scheduleQuoteMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param scheduleQuoteId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public ScheduleQuote selectScheduleQuoteById(Long scheduleQuoteId)
    {
        return scheduleQuoteMapper.selectScheduleQuoteById(scheduleQuoteId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param scheduleQuote 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ScheduleQuote> selectScheduleQuoteList(ScheduleQuote scheduleQuote)
    {
        return scheduleQuoteMapper.selectScheduleQuoteList(scheduleQuote);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param scheduleQuote 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertScheduleQuote(ScheduleQuote scheduleQuote)
    {
        return scheduleQuoteMapper.insertScheduleQuote(scheduleQuote);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param scheduleQuote 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateScheduleQuote(ScheduleQuote scheduleQuote)
    {
        return scheduleQuoteMapper.updateScheduleQuote(scheduleQuote);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScheduleQuoteByIds(String ids)
    {
        return scheduleQuoteMapper.deleteScheduleQuoteByIds(Convert.toStrArray(ids));
    }

    @Override
    public int deleteScheduleQuoteBySchId(Long schId) {
        return scheduleQuoteMapper.deleteScheduleQuoteBySchId(schId);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param scheduleQuoteId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteScheduleQuoteById(Long scheduleQuoteId)
    {
        return scheduleQuoteMapper.deleteScheduleQuoteById(scheduleQuoteId);
    }
}
