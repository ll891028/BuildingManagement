package com.liulin.web.controller.moduler;

import java.util.List;

import com.liulin.common.utils.ShiroUtils;
import com.liulin.system.domain.*;
import com.liulin.system.service.IScheduleAssetService;
import com.liulin.system.service.IScheduleQuoteService;
import com.liulin.system.service.IServizeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.liulin.common.annotation.Log;
import com.liulin.common.enums.BusinessType;
import com.liulin.system.service.IScheduleService;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.common.core.page.TableDataInfo;

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
        startPage();
        List<Schedule> list = scheduleService.selectScheduleList(schedule);
        return getDataTable(list);
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

        ScheduleAsset assetQuery = new ScheduleAsset();
        assetQuery.setSchId(schId);
        List<ScheduleAsset> taskAssets = scheduleAssetService.selectScheduleAssetList(assetQuery);
        mmap.put("taskAssets",taskAssets);

        ScheduleQuote quoteQuery = new ScheduleQuote();
        quoteQuery.setScheduleId(schId);
        List<ScheduleQuote> taskQuotes = scheduleQuoteService.selectScheduleQuoteList(quoteQuery);
        mmap.put("taskQuotes",taskQuotes);
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
    @GetMapping("/quotePriceList/{taskId}")
    public String quoteList(@PathVariable Long taskId,ModelMap mmp)
    {
        mmp.put("taskId",taskId);
        return prefix + "/quoteList";
    }

    /**
     * 查询selectSupplier列表
     */
    @RequiresPermissions("event:schedule:list")
    @PostMapping("/quoteList/list")
    @ResponseBody
    public TableDataInfo quoteList(ScheduleQuote scheduleQuote)
    {
        startPage();
        ScheduleQuote quoteQuery = new ScheduleQuote();
        quoteQuery.setScheduleId(scheduleQuote.getScheduleId());
        List<ScheduleQuote> scheduleQuotes = scheduleQuoteService.selectScheduleQuoteList(quoteQuery);
        return getDataTable(scheduleQuotes);
    }

    /**
     * 修改保存QuorePrice
     */
    @RequiresPermissions("event:schedule:edit")
    @Log(title = "Task", businessType = BusinessType.UPDATE)
    @PostMapping("/saveQuotePrice")
    @ResponseBody
    public AjaxResult saveQuotePrice(ScheduleQuote scheduleQuote)
    {
        return toAjax(scheduleQuoteService.updateScheduleQuote(scheduleQuote));
    }
}
