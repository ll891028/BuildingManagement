package com.liulin.system.service.impl;

import java.util.Date;
import java.util.List;
import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.system.domain.*;
import com.liulin.system.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.ScheduleMapper;
import com.liulin.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private IScheduleDetailService scheduleDetailService;

    @Autowired
    private IAttachmentService attachmentService;


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

    @Override
    public List<ReportDto> selectReportList(ReportDto reportDto) {
        return scheduleMapper.selectReportList(reportDto);
    }

    /**
     * 新增schedule
     * 
     * @param schedule schedule
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int  insertSchedule(Schedule schedule)
    {
        schedule.setCreateTime(DateUtils.getNowDate());

        String[] attachmentUrls = schedule.getAttachmentUrls().split(",");
        String[] originalFileNames = schedule.getOriginalFileNames().split(",");
        String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                schedule.getCreateBy(),schedule.getBuildingId() ,schedule.getCompanyId() );
        schedule.setAttachmentIds(attachmentIds);
        int result = scheduleMapper.insertSchedule(schedule);
        if(CollectionUtils.isNotEmpty(schedule.getAssetIds())){
            for (Long assetId : schedule.getAssetIds()) {
                ScheduleAsset scheduleAsset = new ScheduleAsset();
                scheduleAsset.setSchId(schedule.getSchId());
                scheduleAsset.setAssetId(assetId);
                scheduleAssetService.insertScheduleAsset(scheduleAsset);
            }

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
        generateScheduleDetail(schedule,schedule.getStartDate());
        return result;
    }

    /**
     * 修改schedule
     * 
     * @param schedule schedule
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSchedule(Schedule schedule)
    {
        schedule.setUpdateTime(DateUtils.getNowDate());
        scheduleAssetService.deleteScheduleAssetBySchId(schedule.getSchId());
        scheduleQuoteService.deleteScheduleQuoteBySchId(schedule.getSchId());

        if(StringUtils.isNotEmpty(schedule.getAttachmentUrls())){
            String[] attachmentUrls = schedule.getAttachmentUrls().split(",");
            String[] originalFileNames = schedule.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                    schedule.getUpdateBy(),schedule.getBuildingId() ,schedule.getCompanyId() );
            schedule.setAttachmentIds(attachmentIds);
        }


        if(CollectionUtils.isNotEmpty(schedule.getAssetIds())){
            for (Long assetId : schedule.getAssetIds()) {
                ScheduleAsset scheduleAsset = new ScheduleAsset();
                scheduleAsset.setSchId(schedule.getSchId());
                scheduleAsset.setAssetId(assetId);
                scheduleAssetService.insertScheduleAsset(scheduleAsset);
            }

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
        if(schedule.getStatus().equals(Schedule.INACTIVE)){
            scheduleDetailService.changeDeleteStatusPendingBySchId(schedule.getSchId(),1);
        }else if(schedule.getStatus().equals(Schedule.ACTIVE)){
            scheduleDetailService.changeDeleteStatusPendingBySchId(schedule.getSchId(),0);
        }
        Schedule scheduleOld = this.selectScheduleById(schedule.getSchId());
        Long timeOld = scheduleOld.getStartDate().getTime();
        Long time = schedule.getStartDate().getTime();
        if(!time.equals(timeOld)){
            ScheduleDetail detailQuery = new ScheduleDetail();
            detailQuery.setSchId(schedule.getSchId());
            detailQuery.setStatus(Schedule.PENDING);
            //查询最近的一次detail数据
            List<ScheduleDetail> scheduleDetails = scheduleDetailService.selectScheduleDetailList(detailQuery);
            for (ScheduleDetail detail : scheduleDetails) {
                scheduleDetailService.deleteScheduleDetailById(detail.getSchDetailId());
            }
            generateScheduleDetail(schedule,schedule.getStartDate());
        }
        return scheduleMapper.updateSchedule(schedule);
    }

    @Override
    public int updateScheduleAttachment(Schedule schedule) {

        if(StringUtils.isNotEmpty(schedule.getAttachmentUrls())){
            String[] attachmentUrls = schedule.getAttachmentUrls().split(",");
            String[] originalFileNames = schedule.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,schedule.getCreateBy(),schedule.getBuildingId() ,schedule.getCompanyId());
            schedule.setAttachmentIds(attachmentIds);
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

    @Override
    public void generateScheduleDetail(Schedule schedule,Date startDate) {

        Date now = new Date();
        //查询最近的一次detail数据
        ScheduleDetail detail = scheduleDetailService.selectScheduleDetailBySchIdNearBy(schedule.getSchId());
        if(detail!=null){
            Date schDate = detail.getSchDate();
            if(DateUtils.diffCurrentMonth(schDate,startDate)){
                if(schedule.getFrequency()==Schedule.MONTHLY){
                    //插入一条数据
                    ScheduleDetail saver = new ScheduleDetail();
                    BeanUtils.copyProperties(detail,saver);
                    saver.setSchDetailId(null);
                    saver.setCreateTime(now);
                    saver.setSchDate(DateUtils.addTime(startDate,Schedule.MONTHLY));
                    saver.setStatus(Schedule.PENDING);
                    scheduleDetailService.insertScheduleDetail(saver);
                }else if(schedule.getFrequency()==Schedule.QUARTERLY){
                    //插入一条数据
                    ScheduleDetail saver = new ScheduleDetail();
                    BeanUtils.copyProperties(detail,saver);
                    saver.setSchDetailId(null);
                    saver.setCreateTime(now);
                    saver.setSchDate(DateUtils.addTime(startDate,Schedule.QUARTERLY));
                    saver.setStatus(Schedule.PENDING);
                    scheduleDetailService.insertScheduleDetail(saver);
                }else if(schedule.getFrequency()==Schedule.HALF_YEARLY){
                    //插入一条数据
                    ScheduleDetail saver = new ScheduleDetail();
                    BeanUtils.copyProperties(detail,saver);
                    saver.setSchDetailId(null);
                    saver.setCreateTime(now);
                    saver.setSchDate(DateUtils.addTime(startDate,Schedule.HALF_YEARLY));
                    saver.setStatus(Schedule.PENDING);
                    scheduleDetailService.insertScheduleDetail(saver);
                }else if(schedule.getFrequency()==Schedule.YEARLY){
                    //插入一条数据
                    ScheduleDetail saver = new ScheduleDetail();
                    BeanUtils.copyProperties(detail,saver);
                    saver.setSchDetailId(null);
                    saver.setCreateTime(now);
                    saver.setSchDate(DateUtils.addTime(startDate,Schedule.YEARLY));
                    saver.setStatus(Schedule.PENDING);
                    scheduleDetailService.insertScheduleDetail(saver);
                }
            }else {
                if(schedule.getFrequency()==Schedule.MONTHLY){
                    //插入两条数据
                    ScheduleDetail saver = new ScheduleDetail();
                    BeanUtils.copyProperties(detail,saver);
                    saver.setSchDetailId(null);
                    saver.setCreateTime(now);
                    saver.setSchDate(startDate);
                    saver.setStatus(Schedule.PENDING);
                    scheduleDetailService.insertScheduleDetail(saver);
                    saver.setSchDetailId(null);
                    saver.setSchDate(DateUtils.addTime(saver.getSchDate(),Schedule.MONTHLY));
                    scheduleDetailService.insertScheduleDetail(saver);
                }else if(schedule.getFrequency()==Schedule.QUARTERLY){
                    //插入两条数据
                    ScheduleDetail saver = new ScheduleDetail();
                    BeanUtils.copyProperties(detail,saver);
                    saver.setSchDetailId(null);
                    saver.setCreateTime(now);
                    saver.setSchDate(startDate);
                    saver.setStatus(Schedule.PENDING);
                    scheduleDetailService.insertScheduleDetail(saver);
                    saver.setSchDetailId(null);
                    saver.setSchDate(DateUtils.addTime(saver.getSchDate(),Schedule.QUARTERLY));
                    scheduleDetailService.insertScheduleDetail(saver);
                }else if(schedule.getFrequency()==Schedule.HALF_YEARLY){
                    //插入两条数据
                    ScheduleDetail saver = new ScheduleDetail();
                    BeanUtils.copyProperties(detail,saver);
                    saver.setSchDetailId(null);
                    saver.setCreateTime(now);
                    saver.setSchDate(startDate);
                    saver.setStatus(Schedule.PENDING);
                    scheduleDetailService.insertScheduleDetail(saver);
                    saver.setSchDetailId(null);
                    saver.setSchDate(DateUtils.addTime(saver.getSchDate(),Schedule.HALF_YEARLY));
                    scheduleDetailService.insertScheduleDetail(saver);
                }else if(schedule.getFrequency()==Schedule.YEARLY){
                    //插入两条数据
                    ScheduleDetail saver = new ScheduleDetail();
                    BeanUtils.copyProperties(detail,saver);
                    saver.setSchDetailId(null);
                    saver.setCreateTime(now);
                    saver.setSchDate(startDate);
                    saver.setStatus(Schedule.PENDING);
                    scheduleDetailService.insertScheduleDetail(saver);
                    saver.setSchDetailId(null);
                    saver.setSchDate(DateUtils.addTime(saver.getSchDate(),Schedule.YEARLY));
                    scheduleDetailService.insertScheduleDetail(saver);
                }
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
