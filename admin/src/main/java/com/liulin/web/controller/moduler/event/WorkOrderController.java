package com.liulin.web.controller.moduler.event;

import com.liulin.common.annotation.Log;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.core.page.TableDataInfo;
import com.liulin.common.enums.BusinessType;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.system.domain.WorkOrder;
import com.liulin.system.service.IWorkOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Work OrderController
 * 
 * @author liulin
 * @date 2022-02-08
 */
@Controller
@RequestMapping("/event/workOrder")
public class WorkOrderController extends BaseController
{
    private String prefix = "event/workOrder";

    @Autowired
    private IWorkOrderService workOrderService;

    @RequiresPermissions("event:workOrder:view")
    @GetMapping()
    public String workOrder()
    {
        return prefix + "/workOrder";
    }

    /**
     * 查询Work Order列表
     */
    @RequiresPermissions("event:workOrder:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WorkOrder workOrder)
    {
        workOrder.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        startPage();
        List<WorkOrder> list = workOrderService.selectWorkOrderList(workOrder);
        return getDataTable(list);
    }

    /**
     * 导出Work Order列表
     */
    @RequiresPermissions("event:workOrder:export")
    @Log(title = "Work Order", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WorkOrder workOrder)
    {
        workOrder.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        List<WorkOrder> list = workOrderService.selectWorkOrderList(workOrder);
        ExcelUtil<WorkOrder> util = new ExcelUtil<WorkOrder>(WorkOrder.class);
        return util.exportExcel(list, "Work Order数据");
    }

    /**
     * 新增Work Order
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存Work Order
     */
    @RequiresPermissions("event:workOrder:add")
    @Log(title = "Work Order", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WorkOrder workOrder)
    {
        return toAjax(workOrderService.insertWorkOrder(workOrder));
    }

    /**
     * 修改Work Order
     */
    @GetMapping("/edit/{workOrderId}")
    public String edit(@PathVariable("workOrderId") Long workOrderId, ModelMap mmap)
    {
        WorkOrder workOrder = workOrderService.selectWorkOrderById(workOrderId);
        mmap.put("workOrder", workOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存Work Order
     */
    @RequiresPermissions("event:workOrder:edit")
    @Log(title = "Work Order", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WorkOrder workOrder)
    {
        return toAjax(workOrderService.updateWorkOrder(workOrder));
    }

    /**
     * 删除Work Order
     */
    @RequiresPermissions("event:workOrder:remove")
    @Log(title = "Work Order", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(workOrderService.deleteWorkOrderByIds(ids));
    }
}
