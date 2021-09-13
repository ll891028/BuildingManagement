package com.liulin.system.service.impl;

import java.util.List;
import com.liulin.common.utils.DateUtils;
import com.liulin.system.domain.*;
import com.liulin.system.service.IScheduleAssetService;
import com.liulin.system.service.IScheduleQuoteService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.ScheduleMapper;
import com.liulin.system.service.IScheduleService;
import com.liulin.common.core.text.Convert;

/**
 * scheduleService业务层处理
 * 
 * @author liulin
 * @date 2021-09-09
 */
@Service
public class ScheduleServiceImpl implements IScheduleService 
{
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private IScheduleAssetService scheduleAssetService;

    @Autowired
    private IScheduleQuoteService scheduleQuoteService;


    /**
     * 查询schedule
     * 
     * @param schId scheduleID
     * @return schedule
     */
    @Override
    public Schedule selectScheduleById(Long schId)
    {
        return scheduleMapper.selectScheduleById(schId);
    }

    /**
     * 查询schedule列表
     * 
     * @param schedule schedule
     * @return schedule
     */
    @Override
    public List<Schedule> selectScheduleList(Schedule schedule)
    {
        return scheduleMapper.selectScheduleList(schedule);
    }

    /**
     * 新增schedule
     * 
     * @param schedule schedule
     * @return 结果
     */
    @Override
    public int insertSchedule(Schedule schedule)
    {
        schedule.setCreateTime(DateUtils.getNowDate());
        int result = scheduleMapper.insertSchedule(schedule);
        if(CollectionUtils.isNotEmpty(schedule.getAssetIds())){
            for (Long assetId : schedule.getAssetIds()) {
                ScheduleAsset scheduleAsset = new ScheduleAsset();
                scheduleAsset.setSchId(schedule.getSchId());
                scheduleAsset.setAssetId(assetId);
                scheduleAssetService.insertScheduleAsset(scheduleAsset);
            }

        }
        if(schedule.getNeedWorkOrder().equals(Task.N)){
            schedule.setOrderInstruction(null);
            schedule.setOrderSupplierId(null);
            schedule.setOrderStatus(null);
        }
        if(schedule.getNeedQuote().equals(Task.N)){
            schedule.setQuoteInstruction(null);
            schedule.setQuoteStatus(null);
            schedule.setQuoteSupplierIds(null);
        }
        if(CollectionUtils.isNotEmpty(schedule.getQuoteSupplierIds())){
            for (Long supplierId : schedule.getQuoteSupplierIds()) {
                ScheduleQuote saver = new ScheduleQuote();
                saver.setScheduleId(schedule.getSchId());
                saver.setSupplierId(supplierId);
                scheduleQuoteService.insertScheduleQuote(saver);
            }
        }
        return result;
    }

    /**
     * 修改schedule
     * 
     * @param schedule schedule
     * @return 结果
     */
    @Override
    public int updateSchedule(Schedule schedule)
    {
        schedule.setUpdateTime(DateUtils.getNowDate());
        scheduleAssetService.deleteScheduleAssetBySchId(schedule.getSchId());
        scheduleQuoteService.deleteScheduleQuoteBySchId(schedule.getSchId());

        if(CollectionUtils.isNotEmpty(schedule.getAssetIds())){
            for (Long assetId : schedule.getAssetIds()) {
                ScheduleAsset scheduleAsset = new ScheduleAsset();
                scheduleAsset.setSchId(schedule.getSchId());
                scheduleAsset.setAssetId(assetId);
                scheduleAssetService.insertScheduleAsset(scheduleAsset);
            }

        }
        if(schedule.getNeedWorkOrder().equals(Task.N)){
            schedule.setOrderInstruction(null);
            schedule.setOrderSupplierId(null);
            schedule.setOrderStatus(null);
        }
        if(schedule.getNeedQuote().equals(Task.N)){
            schedule.setQuoteInstruction(null);
            schedule.setQuoteStatus(null);
            schedule.setQuoteSupplierIds(null);
        }
        if(CollectionUtils.isNotEmpty(schedule.getQuoteSupplierIds())){
            for (Long supplierId : schedule.getQuoteSupplierIds()) {
                ScheduleQuote saver = new ScheduleQuote();
                saver.setScheduleId(schedule.getSchId());
                saver.setSupplierId(supplierId);
                scheduleQuoteService.insertScheduleQuote(saver);
            }
        }
        return scheduleMapper.updateSchedule(schedule);
    }

    /**
     * 删除schedule对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScheduleByIds(String ids)
    {
        return scheduleMapper.deleteScheduleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除schedule信息
     * 
     * @param schId scheduleID
     * @return 结果
     */
    @Override
    public int deleteScheduleById(Long schId)
    {
        return scheduleMapper.deleteScheduleById(schId);
    }
}
