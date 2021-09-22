package com.liulin.system.service.impl;

import java.util.List;
import com.liulin.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.ScheduleDetailMapper;
import com.liulin.system.domain.ScheduleDetail;
import com.liulin.system.service.IScheduleDetailService;
import com.liulin.common.core.text.Convert;

/**
 * scheduleDetailService业务层处理
 * 
 * @author liulin
 * @date 2021-09-22
 */
@Service
public class ScheduleDetailServiceImpl implements IScheduleDetailService 
{
    @Autowired
    private ScheduleDetailMapper scheduleDetailMapper;

    /**
     * 查询scheduleDetail
     * 
     * @param schDetailId scheduleDetailID
     * @return scheduleDetail
     */
    @Override
    public ScheduleDetail selectScheduleDetailById(Long schDetailId)
    {
        return scheduleDetailMapper.selectScheduleDetailById(schDetailId);
    }

    @Override
    public ScheduleDetail selectScheduleDetailBySchIdNearBy(Long schId) {
        return scheduleDetailMapper.selectScheduleDetailBySchIdNearBy(schId);
    }

    /**
     * 查询scheduleDetail列表
     * 
     * @param scheduleDetail scheduleDetail
     * @return scheduleDetail
     */
    @Override
    public List<ScheduleDetail> selectScheduleDetailList(ScheduleDetail scheduleDetail)
    {
        return scheduleDetailMapper.selectScheduleDetailList(scheduleDetail);
    }

    /**
     * 新增scheduleDetail
     * 
     * @param scheduleDetail scheduleDetail
     * @return 结果
     */
    @Override
    public int insertScheduleDetail(ScheduleDetail scheduleDetail)
    {
        scheduleDetail.setCreateTime(DateUtils.getNowDate());
        return scheduleDetailMapper.insertScheduleDetail(scheduleDetail);
    }

    /**
     * 修改scheduleDetail
     * 
     * @param scheduleDetail scheduleDetail
     * @return 结果
     */
    @Override
    public int updateScheduleDetail(ScheduleDetail scheduleDetail)
    {
        scheduleDetail.setUpdateTime(DateUtils.getNowDate());
        return scheduleDetailMapper.updateScheduleDetail(scheduleDetail);
    }

    /**
     * 删除scheduleDetail对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScheduleDetailByIds(String ids)
    {
        return scheduleDetailMapper.deleteScheduleDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除scheduleDetail信息
     * 
     * @param schDetailId scheduleDetailID
     * @return 结果
     */
    @Override
    public int deleteScheduleDetailById(Long schDetailId)
    {
        return scheduleDetailMapper.deleteScheduleDetailById(schDetailId);
    }
}
