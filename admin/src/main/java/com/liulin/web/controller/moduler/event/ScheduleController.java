package com.liulin.web.controller.moduler.event;

import com.liulin.common.annotation.Log;
import com.liulin.common.config.ServerConfig;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.core.page.TableDataInfo;
import com.liulin.common.enums.BusinessType;
import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.system.domain.*;
import com.liulin.system.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * scheduleController
 * 
 * @author liulin
 * @date 2021-09-09
 */
@Controller
@RequestMapping("/event/schedule")
public class ScheduleController extends BaseController
{
    private String prefix = "event/schedule";

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IServizeService servizeService;

    @Autowired
    private IScheduleAssetService scheduleAssetService;

    @Autowired
    private IScheduleQuoteService scheduleQuoteService;

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IScheduleDetailService scheduleDetailService;

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private ISafetyCheckService safetyCheckService;


    @RequiresPermissions("event:schedule:view")
    @GetMapping()
    public String schedule(ModelMap mmp)
    {
        Servize query = new Servize();
        query.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        List<Servize> servizes = servizeService.selectServizeList(query);
        mmp.put("servizes",servizes);
        return prefix + "/schedule";
    }

    /**
     * 查询schedule列表
     */
    @RequiresPermissions("event:schedule:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Schedule schedule)
    {
        schedule.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        startPage();
        List<Schedule> list = scheduleService.selectScheduleList(schedule);
        return getDataTable(list);
    }

    /**
     * 查询schedule详情
     */
    @RequiresPermissions("event:schedule:list")
    @GetMapping("/detail/{schId}")
    public String detail(@PathVariable Long schId,ModelMap mmp)
    {
        Servize query = new Servize();
        query.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        List<Servize> servizes = servizeService.selectServizeList(query);
        mmp.put("servizes",servizes);

        Schedule schedule = scheduleService.selectScheduleById(schId);
        mmp.put("schedule", schedule);

        ScheduleAsset assetQuery = new ScheduleAsset();
        assetQuery.setSchId(schId);
        List<ScheduleAsset> taskAssets = scheduleAssetService.selectScheduleAssetList(assetQuery);
        mmp.put("taskAssets",taskAssets);

        ScheduleQuote quoteQuery = new ScheduleQuote();
        quoteQuery.setScheduleId(schId);
        List<ScheduleQuote> taskQuotes = scheduleQuoteService.selectScheduleQuoteList(quoteQuery);
        mmp.put("taskQuotes",taskQuotes);
        return prefix + "/detail";
    }

    /**
     * 导出schedule列表
     */
    @RequiresPermissions("event:schedule:export")
    @Log(title = "schedule", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Schedule schedule)
    {
        List<Schedule> list = scheduleService.selectScheduleList(schedule);
        ExcelUtil<Schedule> util = new ExcelUtil<Schedule>(Schedule.class);
        return util.exportExcel(list, "schedule数据");
    }

    /**
     * 新增schedule
     */
    @GetMapping("/add")
    public String add(ModelMap mmp)
    {
        Servize query = new Servize();
        query.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        List<Servize> servizes = servizeService.selectServizeList(query);
        mmp.put("servizes",servizes);
        return prefix + "/add";
    }

    /**
     * 新增保存schedule
     */
    @RequiresPermissions("event:schedule:add")
    @Log(title = "schedule", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Schedule schedule)
    {
        schedule.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        schedule.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        schedule.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(scheduleService.insertSchedule(schedule));
    }

    /**
     * 修改schedule
     */
    @GetMapping("/edit/{schId}")
    public String edit(@PathVariable("schId") Long schId, ModelMap mmap)
    {

        Servize query = new Servize();
        query.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        List<Servize> servizes = servizeService.selectServizeList(query);
        mmap.put("servizes",servizes);

        Schedule schedule = scheduleService.selectScheduleById(schId);
        mmap.put("schedule", schedule);
        for (Servize servize : servizes) {
            if (servize.getServiceId().equals(schedule.getServiceId())) {
                servize.setSelected(true);
                break;
            }
        }
        ScheduleAsset assetQuery = new ScheduleAsset();
        assetQuery.setSchId(schId);
        List<ScheduleAsset> taskAssets = scheduleAssetService.selectScheduleAssetList(assetQuery);
        mmap.put("taskAssets",taskAssets);

        ScheduleQuote quoteQuery = new ScheduleQuote();
        quoteQuery.setScheduleId(schId);
        List<ScheduleQuote> taskQuotes = scheduleQuoteService.selectScheduleQuoteList(quoteQuery);
        mmap.put("taskQuotes",taskQuotes);

        String attachmentIds = schedule.getAttachmentIds();
        if(StringUtils.isNotEmpty(attachmentIds)){
            String[] attachmentIdStrArray = attachmentIds.split(",");
            int [] attachmentIdArray =
                    Arrays.asList(attachmentIdStrArray).stream().mapToInt(Integer::parseInt).toArray();
            List<Attachment> attachments = attachmentService.selectAttachmentByIds(attachmentIdArray);
            if(CollectionUtils.isNotEmpty(attachments)){
                mmap.put("attachments",attachments);
            }
        }
        return prefix + "/edit";
    }

    /**
     * 修改保存schedule
     */
    @RequiresPermissions("event:schedule:edit")
    @Log(title = "schedule", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Schedule schedule)
    {
        schedule.setUpdateBy(ShiroUtils.getLoginName());
        schedule.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        schedule.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(scheduleService.updateSchedule(schedule));
    }

    /**
     * 删除schedule
     */
    @RequiresPermissions("event:schedule:remove")
    @Log(title = "schedule", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(scheduleService.deleteScheduleByIds(ids));
    }


    @RequiresPermissions("event:schedule:view")
    @GetMapping("/quotePriceList/{scheduleId}")
    public String quoteList(@PathVariable Long scheduleId,ModelMap mmp)
    {
        mmp.put("scheduleId",scheduleId);
        return prefix + "/quoteList";
    }

    /**
     * 查询quoteList列表
     */
    @RequiresPermissions("event:schedule:list")
    @PostMapping("/quoteList/list")
    @ResponseBody
    public TableDataInfo quoteList(ScheduleQuote scheduleQuote)
    {
        startPage();
        List<ScheduleQuote> scheduleQuotes = scheduleQuoteService.selectScheduleQuoteList(scheduleQuote);
        return getDataTable(scheduleQuotes);
    }

    /**
     * 修改保存QuorePrice
     */
    @RequiresPermissions("event:schedule:edit")
    @Log(title = "Schedule", businessType = BusinessType.UPDATE)
    @PostMapping("/saveQuotePrice")
    @ResponseBody
    public AjaxResult saveQuotePrice(ScheduleQuote scheduleQuote)
    {
        return toAjax(scheduleQuoteService.updateScheduleQuote(scheduleQuote));
    }

    @RequiresPermissions("event:schedule:list")
    @GetMapping("/eventList")
    @ResponseBody
    public AjaxResult eventList(@RequestParam Long from,@RequestParam Long to)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = new Date(from);
        Date endDate = new Date(to);
        ScheduleDetail scheduleQuery = new ScheduleDetail();
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("beginSchDate",simpleDateFormat.format(startDate));
        paramsMap.put("endSchDate",simpleDateFormat.format(endDate));
        scheduleQuery.setParams(paramsMap);
        scheduleQuery.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        List<Map> eventList = new ArrayList<>();
        List<ScheduleDetail> list = scheduleDetailService.selectScheduleDetailList(scheduleQuery);

        Task taskQuery = new Task();
        taskQuery.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        paramsMap.put("beginTimeScheduled",simpleDateFormat.format(startDate));
        paramsMap.put("endTimeScheduled",simpleDateFormat.format(endDate));
        taskQuery.setParams(paramsMap);
        List<Task> tasks = taskService.selectTaskList(taskQuery);

        SafetyCheck checkQuery = new SafetyCheck();
        checkQuery.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
//        checkQuery.setStatus();
        paramsMap.put("beginDateSchedule",simpleDateFormat.format(startDate));
        paramsMap.put("endDateSchedule",simpleDateFormat.format(endDate));
        checkQuery.setParams(paramsMap);
        List<SafetyCheck> safetyChecks = safetyCheckService.selectSafetyCheckList(checkQuery);

        String prefixUrl = new ServerConfig().getUrl();
        if(CollectionUtils.isNotEmpty(list)){
            for (ScheduleDetail schedule : list) {
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(schedule.getSchDate());
//                cal.add(Calendar.DATE,1);
//                Date end =cal.getTime();
                Map<String ,String> map = new HashMap<>();
                map.put("id", String.valueOf(schedule.getSchId()));
                map.put("title",schedule.getSchName());
                map.put("url", prefixUrl+"/event/schedule/detail/"+schedule.getSchId());
                map.put("class","event-success");
                map.put("start", String.valueOf(schedule.getSchDate().getTime()));
                map.put("end",String.valueOf(schedule.getSchDate().getTime()));
                eventList.add(map);
            }
        }
        if(CollectionUtils.isNotEmpty(tasks)){
            for (Task task : tasks) {
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(task.getTimeScheduled());
//                cal.add(Calendar.DATE,1);
//                Date end =cal.getTime();
                Map<String ,String> map = new HashMap<>();
                map.put("id", String.valueOf(task.getTaskId()));
                map.put("title",task.getTaskName()+" "+ DateUtils.parseDateToStr("dd-MM-yyyy HH:mm",
                        task.getTimeScheduled()));
                map.put("url",prefixUrl+"/event/task/detail/"+task.getTaskId());
                if(task.getTaskType()==1) {
                    map.put("class","event-primary");
                }else{
                    map.put("class","event-info");
                }

                map.put("start", String.valueOf(task.getTimeScheduled().getTime()));
                map.put("end",String.valueOf(task.getTimeScheduled().getTime()));
                eventList.add(map);
            }
        }

        if(CollectionUtils.isNotEmpty(safetyChecks)){
            for (SafetyCheck safetyCheck : safetyChecks) {
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(safetyCheck.getDateSchedule());
//                cal.add(Calendar.DATE,1);
//                Date end =cal.getTime();
                Map<String ,String> map = new HashMap<>();
                map.put("id", String.valueOf(safetyCheck.getSafetyCheckId()));
                map.put("title","Safety Check "+ DateUtils.parseDateToStr("dd-MM-yyyy",
                        safetyCheck.getDateSchedule()));
                map.put("url",prefixUrl+"/event/safetyCheck/detail/"+safetyCheck.getSafetyCheckId());
                map.put("class","event-warning");
                map.put("start", String.valueOf(safetyCheck.getDateSchedule().getTime()));
                map.put("end",String.valueOf(safetyCheck.getDateSchedule().getTime()));
                eventList.add(map);
            }
        }
        AjaxResult result = new AjaxResult();
        result.put("success",1);
        result.put("result",eventList);
        return result;
    }


    @RequiresPermissions("event:schedule:view")
    @GetMapping("/schDetailList/{scheduleId}")
    public String schDetailList(@PathVariable Long scheduleId,ModelMap mmp)
    {
        mmp.put("scheduleId",scheduleId);
        return prefix + "/schDetailList";
    }

    /**
     * 查询schDetailList列表
     */
    @RequiresPermissions("event:schedule:list")
    @PostMapping("/schDetailList/list")
    @ResponseBody
    public TableDataInfo schDetailList(ScheduleDetail scheduleDetail)
    {
        startPage();
        List<ScheduleDetail> scheduleDetails = scheduleDetailService.selectScheduleDetailList(scheduleDetail);
        return getDataTable(scheduleDetails);
    }


    /**
     * 修改scheduleDetail
     */
    @GetMapping("/detailEdit/{schDetailId}")
    public String editDetail(@PathVariable("schDetailId") Long schDetailId, ModelMap mmap)
    {
        ScheduleDetail detail = scheduleDetailService.selectScheduleDetailById(schDetailId);
        mmap.put("detail",detail);

        String attachmentIds = detail.getAttachmentIds();
        if(StringUtils.isNotEmpty(attachmentIds)){
            String[] attachmentIdStrArray = attachmentIds.split(",");
            int [] attachmentIdArray =
                    Arrays.asList(attachmentIdStrArray).stream().mapToInt(Integer::parseInt).toArray();
            List<Attachment> attachments = attachmentService.selectAttachmentByIds(attachmentIdArray);
            if(CollectionUtils.isNotEmpty(attachments)){
                mmap.put("attachments",attachments);
            }
        }
        return prefix + "/schDetail";
    }

    /**
     * 修改保存schedule
     */
    @RequiresPermissions("event:schedule:edit")
    @Log(title = "schedule", businessType = BusinessType.UPDATE)
    @PostMapping("/editDetail")
    @ResponseBody
    public AjaxResult editDetailSave(ScheduleDetail scheduleDetail)
    {
        scheduleDetail.setUpdateBy(ShiroUtils.getLoginName());
        scheduleDetail.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        scheduleDetail.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(scheduleDetailService.updateScheduleDetail(scheduleDetail));
    }

    /**
     * 删除附件
     */
    @Log(title = "删除attachment附件", businessType = BusinessType.DELETE)
    @RequiresPermissions("event:schedule:remove")
    @PostMapping("/attachment/remove")
    @ResponseBody
    public AjaxResult remove(Schedule schedule)
    {
        schedule.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        schedule.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        schedule.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(scheduleService.updateScheduleAttachment(schedule));
    }

    /**
     * 删除detail附件
     */
    @Log(title = "删除detail附件", businessType = BusinessType.DELETE)
    @RequiresPermissions("event:schedule:remove")
    @PostMapping("/scheduleDetail/attachment/remove")
    @ResponseBody
    public AjaxResult scheduleDetailRemove(ScheduleDetail scheduleDetail)
    {
        scheduleDetail.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        scheduleDetail.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        scheduleDetail.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(scheduleDetailService.updateScheduleAttachment(scheduleDetail));
    }

}
