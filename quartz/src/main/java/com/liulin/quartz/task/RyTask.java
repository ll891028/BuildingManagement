package com.liulin.quartz.task;

import com.liulin.common.utils.DateUtils;
import com.liulin.system.domain.Schedule;
import com.liulin.system.domain.ScheduleDetail;
import com.liulin.system.service.IScheduleDetailService;
import com.liulin.system.service.IScheduleService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.liulin.common.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 定时任务调度测试
 * 
 * @author liulin
 */
@Component("ryTask")
public class RyTask
{
    private static final Logger log = LoggerFactory.getLogger(RyTask.class);

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IScheduleDetailService scheduleDetailService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    public void scheduleDetailJob()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        log.info("开始执行PM Schedule Job,开始时间:{}",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,now));
        Schedule schQuery = new Schedule();
        //查询激活中的任务列表
        schQuery.setStatus(Schedule.ACTIVE);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("endStartDate",simpleDateFormat.format(now));
        schQuery.setParams(paramsMap);
        List<Schedule> schedules = scheduleService.selectScheduleList(schQuery);
        if(CollectionUtils.isNotEmpty(schedules)){
            for (Schedule schedule : schedules) {
                //查询最近的一次detail数据
                ScheduleDetail detail = scheduleDetailService.selectScheduleDetailBySchIdNearBy(schedule.getSchId());
                if(detail!=null){
                    Date schDate = detail.getSchDate();
                    if(!DateUtils.diffCurrentMonth(schDate,now)){
                        continue;
                    }
                    if(schedule.getFrequency()==Schedule.MONTHLY){
                        //插入一条数据
                        ScheduleDetail saver = new ScheduleDetail();
                        BeanUtils.copyProperties(detail,saver);
                        saver.setSchDetailId(null);
                        saver.setCreateTime(now);
                        saver.setSchDate(DateUtils.addTime(schDate,Schedule.MONTHLY));
                        saver.setStatus(Schedule.PENDING);
                        scheduleDetailService.insertScheduleDetail(saver);
                    }else if(schedule.getFrequency()==Schedule.QUARTERLY){
                        //插入一条数据
                        ScheduleDetail saver = new ScheduleDetail();
                        BeanUtils.copyProperties(detail,saver);
                        saver.setSchDetailId(null);
                        saver.setCreateTime(now);
                        saver.setSchDate(DateUtils.addTime(schDate,Schedule.QUARTERLY));
                        saver.setStatus(Schedule.PENDING);
                        scheduleDetailService.insertScheduleDetail(saver);
                    }else if(schedule.getFrequency()==Schedule.HALF_YEARLY){
                        //插入一条数据
                        ScheduleDetail saver = new ScheduleDetail();
                        BeanUtils.copyProperties(detail,saver);
                        saver.setSchDetailId(null);
                        saver.setCreateTime(now);
                        saver.setSchDate(DateUtils.addTime(schDate,Schedule.HALF_YEARLY));
                        saver.setStatus(Schedule.PENDING);
                        scheduleDetailService.insertScheduleDetail(saver);
                    }else if(schedule.getFrequency()==Schedule.YEARLY){
                        //插入一条数据
                        ScheduleDetail saver = new ScheduleDetail();
                        BeanUtils.copyProperties(detail,saver);
                        saver.setSchDetailId(null);
                        saver.setCreateTime(now);
                        saver.setSchDate(DateUtils.addTime(schDate,Schedule.YEARLY));
                        saver.setStatus(Schedule.PENDING);
                        scheduleDetailService.insertScheduleDetail(saver);
                    }
                }else{
                    if(schedule.getFrequency()==Schedule.MONTHLY){
                        //插入两条数据
                        ScheduleDetail saver = new ScheduleDetail();
                        saver.setSchId(schedule.getSchId());
                        saver.setCreateTime(now);
                        saver.setSchDate(schedule.getStartDate());
                        saver.setStatus(Schedule.PENDING);
                        scheduleDetailService.insertScheduleDetail(saver);
                        saver.setSchDetailId(null);
                        saver.setSchDate(DateUtils.addTime(schedule.getStartDate(),Schedule.MONTHLY));
                        scheduleDetailService.insertScheduleDetail(saver);
                    }else if(schedule.getFrequency()==Schedule.QUARTERLY){
                        //插入两条数据
                        ScheduleDetail saver = new ScheduleDetail();
                        saver.setSchId(schedule.getSchId());
                        saver.setCreateTime(now);
                        saver.setSchDate(schedule.getStartDate());
                        saver.setStatus(Schedule.PENDING);
                        scheduleDetailService.insertScheduleDetail(saver);
                        saver.setSchDetailId(null);
                        saver.setSchDate(DateUtils.addTime(schedule.getStartDate(),Schedule.QUARTERLY));
                        scheduleDetailService.insertScheduleDetail(saver);
                    }else if(schedule.getFrequency()==Schedule.HALF_YEARLY){
                        //插入两条数据
                        ScheduleDetail saver = new ScheduleDetail();
                        saver.setSchId(schedule.getSchId());
                        saver.setCreateTime(now);
                        saver.setSchDate(schedule.getStartDate());
                        saver.setStatus(Schedule.PENDING);
                        scheduleDetailService.insertScheduleDetail(saver);
                        saver.setSchDetailId(null);
                        saver.setSchDate(DateUtils.addTime(schedule.getStartDate(),Schedule.HALF_YEARLY));
                        scheduleDetailService.insertScheduleDetail(saver);
                    }else if(schedule.getFrequency()==Schedule.YEARLY){
                        //插入两条数据
                        ScheduleDetail saver = new ScheduleDetail();
                        saver.setSchId(schedule.getSchId());
                        saver.setCreateTime(now);
                        saver.setSchDate(schedule.getStartDate());
                        saver.setStatus(Schedule.PENDING);
                        scheduleDetailService.insertScheduleDetail(saver);
                        saver.setSchDetailId(null);
                        saver.setSchDate(DateUtils.addTime(schedule.getStartDate(),Schedule.YEARLY));
                        scheduleDetailService.insertScheduleDetail(saver);
                    }
                }
            }
        }

        log.info("执行PM Schedule Job结束,结束时间:{}",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,new Date()));
    }
}
