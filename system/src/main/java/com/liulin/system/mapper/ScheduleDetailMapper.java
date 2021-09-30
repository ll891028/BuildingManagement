package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.ScheduleDetail;
import org.apache.ibatis.annotations.Param;

/**
 * scheduleDetailMapper接口
 * 
 * @author liulin
 * @date 2021-09-22
 */
public interface ScheduleDetailMapper 
{
    /**
     * 查询scheduleDetail
     * 
     * @param schDetailId scheduleDetailID
     * @return scheduleDetail
     */
    public ScheduleDetail selectScheduleDetailById(Long schDetailId);

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
     * 删除scheduleDetail
     * 
     * @param schDetailId scheduleDetailID
     * @return 结果
     */
    public int deleteScheduleDetailById(Long schDetailId);

    /**
     * 批量删除scheduleDetail
     * 
     * @param schDetailIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteScheduleDetailByIds(String[] schDetailIds);

    int changeDeleteStatusPendingBySchId(@Param("schId") Long schId,@Param("isDel") Integer isDel);
}
