package com.liulin.web.controller.moduler;

import java.util.List;
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
import com.liulin.system.domain.Resident;
import com.liulin.system.service.IResidentService;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.common.core.page.TableDataInfo;

/**
 * residentController
 * 
 * @author liulin
 * @date 2021-08-05
 */
@Controller
@RequestMapping("/data/resident")
public class ResidentController extends BaseController
{
    private String prefix = "data/resident";

    @Autowired
    private IResidentService residentService;

    @RequiresPermissions("data:resident:view")
    @GetMapping()
    public String resident()
    {
        return prefix + "/resident";
    }

    /**
     * 查询resident列表
     */
    @RequiresPermissions("data:resident:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Resident resident)
    {
        startPage();
        List<Resident> list = residentService.selectResidentList(resident);
        return getDataTable(list);
    }

    /**
     * 导出resident列表
     */
    @RequiresPermissions("data:resident:export")
    @Log(title = "resident", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Resident resident)
    {
        List<Resident> list = residentService.selectResidentList(resident);
        ExcelUtil<Resident> util = new ExcelUtil<Resident>(Resident.class);
        return util.exportExcel(list, "resident数据");
    }

    /**
     * 新增resident
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存resident
     */
    @RequiresPermissions("data:resident:add")
    @Log(title = "resident", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Resident resident)
    {
        return toAjax(residentService.insertResident(resident));
    }

    /**
     * 修改resident
     */
    @GetMapping("/edit/{residentId}")
    public String edit(@PathVariable("residentId") Long residentId, ModelMap mmap)
    {
        Resident resident = residentService.selectResidentById(residentId);
        mmap.put("resident", resident);
        return prefix + "/edit";
    }

    /**
     * 修改保存resident
     */
    @RequiresPermissions("data:resident:edit")
    @Log(title = "resident", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Resident resident)
    {
        return toAjax(residentService.updateResident(resident));
    }

    /**
     * 删除resident
     */
    @RequiresPermissions("data:resident:remove")
    @Log(title = "resident", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(residentService.deleteResidentByIds(ids));
    }
}
