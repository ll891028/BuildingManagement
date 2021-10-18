package com.liulin.system.service;

import java.util.List;

import com.liulin.system.domain.Schedule;
import com.liulin.system.domain.ScheduleDetail;

/**
 * scheduleDetailService接口
 * 
 * @author liulin
 * @date 2021-09-22
 */
public interface IScheduleDetailService 
{
    /**
     * 查询scheduleDetail
     * 
     * @param schDetailId scheduleDetailID
     * @return scheduleDetail
     */
    public ScheduleDetail selectScheduleDetailById(Long schDetailId);

    /**
     * 查询最近的一次Detail数据
     * @param schId
     * @return
     */
    public ScheduleDetail selectScheduleDetailBySchIdNearBy(Long schId);

    /**
     * 查询scheduleDetail列表
     * 
     * @param scheduleDetail scheduleDetail
     * @return scheduleDetail集合
     */
    public List<ScheduleDetail> selectScheduleDetailList(ScheduleDetail scheduleDetail);

    /**
     * 新增scheduleDetail
     * 
     * @param scheduleDetail scheduleDetail
     * @return 结果
     */
    public int insertScheduleDetail(ScheduleDetail scheduleDetail);

    /**
     * 修改scheduleDetail
     * 
     * @param scheduleDetail scheduleDetail
     * @return 结果
     */
    public int updateScheduleDetail(ScheduleDetail scheduleDetail);

    /**
     * 批量删除scheduleDetail
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScheduleDetailByIds(String ids);

    /**
     * 删除scheduleDetail信息
     * 
     * @param schDetailId scheduleDetailID
     * @return 结果
     */
    public int deleteScheduleDetailById(Long schDetailId);

    public int changeDeleteStatusPendingBySchId(Long schId,Integer isDel);

    public int updateScheduleAttachment(ScheduleDetail scheduleDetail);
}
