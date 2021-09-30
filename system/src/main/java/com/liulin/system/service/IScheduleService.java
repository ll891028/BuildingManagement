package com.liulin.system.service;

import java.util.Date;
import java.util.List;

import com.liulin.system.domain.ReportDto;
import com.liulin.system.domain.Schedule;

/**
 * scheduleService接口
 * 
 * @author liulin
 * @date 2021-09-09
 */
public interface IScheduleService 
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

    public List<ReportDto> selectReportList(ReportDto reportDto);

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

    public int updateScheduleAttachment(Schedule schedule);

    /**
     * 批量删除schedule
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScheduleByIds(String ids);

    /**
     * 删除schedule信息
     * 
     * @param schId scheduleID
     * @return 结果
     */
    public int deleteScheduleById(Long schId);

    void generateScheduleDetail(Schedule schedule, Date startDate);
}
