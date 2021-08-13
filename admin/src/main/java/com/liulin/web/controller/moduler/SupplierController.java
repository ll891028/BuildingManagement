package com.liulin.web.controller.moduler;

import java.util.List;

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
import com.liulin.system.domain.Supplier;
import com.liulin.system.service.ISupplierService;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.common.core.page.TableDataInfo;

/**
 * supplierController
 * 
 * @author liulin
 * @date 2021-08-09
 */
@Controller
@RequestMapping("/data/supplier")
public class SupplierController extends BaseController
{
    private String prefix = "data/supplier";

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IServizeService servizeService;

    @RequiresPermissions("data:supplier:view")
    @GetMapping()
    public String supplier()
    {


        return prefix + "/supplier";
    }

    /**
     * 查询supplier列表
     */
    @RequiresPermissions("data:supplier:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Supplier supplier)
    {
        startPage();
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        return getDataTable(list);
    }

    /**
     * 导出supplier列表
     */
    @RequiresPermissions("data:supplier:export")
    @Log(title = "supplier", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Supplier supplier)
    {
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        ExcelUtil<Supplier> util = new ExcelUtil<Supplier>(Supplier.class);
        return util.exportExcel(list, "supplier数据");
    }

    /**
     * 新增supplier
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存supplier
     */
    @RequiresPermissions("data:supplier:add")
    @Log(title = "supplier", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Supplier supplier)
    {
        return toAjax(supplierService.insertSupplier(supplier));
    }

    /**
     * 修改supplier
     */
    @GetMapping("/edit/{supplierId}")
    public String edit(@PathVariable("supplierId") Long supplierId, ModelMap mmap)
    {
        Supplier supplier = supplierService.selectSupplierById(supplierId);
        mmap.put("supplier", supplier);
        return prefix + "/edit";
    }

    /**
     * 修改保存supplier
     */
    @RequiresPermissions("data:supplier:edit")
    @Log(title = "supplier", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Supplier supplier)
    {
        return toAjax(supplierService.updateSupplier(supplier));
    }

    /**
     * 删除supplier
     */
    @RequiresPermissions("data:supplier:remove")
    @Log(title = "supplier", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(supplierService.deleteSupplierByIds(ids));
    }
}
