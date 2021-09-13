package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.Schedule;

/**
 * scheduleMapper接口
 * 
 * @author liulin
 * @date 2021-09-09
 */
public interface ScheduleMapper 
{
    /**
     * 查询schedule
     * 
     * @param schId scheduleID
     * @return schedule
     */
    public Schedule selectScheduleById(Long schId);

    /**
     * 查询schedule列表
     * 
     * @param schedule schedule
     * @return schedule集合
     */
    public List<Schedule> selectScheduleList(Schedule schedule);

    /**
     * 新增schedule
     * 
     * @param schedule schedule
     * @return 结果
     */
    public int insertSchedule(Schedule schedule);

    /**
     * 修改schedule
     * 
     * @param schedule schedule
     * @return 结果
     */
    public int updateSchedule(Schedule schedule);

    /**
     * 删除schedule
     * 
     * @param schId scheduleID
     * @return 结果
     */
    public int deleteScheduleById(Long schId);

    /**
     * 批量删除schedule
     * 
     * @param schIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteScheduleByIds(String[] schIds);
}
