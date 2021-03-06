package com.liulin.web.controller.moduler.data;

import com.liulin.common.annotation.Log;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.core.page.TableDataInfo;
import com.liulin.common.enums.BusinessType;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.system.domain.Attachment;
import com.liulin.system.domain.CompanyService;
import com.liulin.system.domain.Servize;
import com.liulin.system.domain.Supplier;
import com.liulin.system.service.IAttachmentService;
import com.liulin.system.service.ICompanyServiceService;
import com.liulin.system.service.IServizeService;
import com.liulin.system.service.ISupplierService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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

    @Autowired
    private ICompanyServiceService companyServiceService;

    @Autowired
    private IAttachmentService attachmentService;

    @RequiresPermissions("data:supplier:view")
    @GetMapping()
    public String supplier(ModelMap mmp)
    {
        Servize query = new Servize();
        query.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        List<Servize> serviceList = servizeService.selectServizeList(query);
        mmp.put("serviceList",serviceList);
        return prefix + "/supplier";
    }

    /**
     * ??????supplier??????
     */
    @RequiresPermissions("data:supplier:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Supplier supplier)
    {
        supplier.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        startPage();
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        return getDataTable(list);
    }

    /**
     * ??????supplier??????
     */
    @RequiresPermissions("data:supplier:export")
    @Log(title = "supplier", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Supplier supplier)
    {
        supplier.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        ExcelUtil<Supplier> util = new ExcelUtil<Supplier>(Supplier.class);
        return util.exportExcel(list, "supplier??????");
    }

    /**
     * ??????supplier
     */
    @GetMapping("/add")
    public String add(ModelMap mmp)
    {

        Servize query = new Servize();
        query.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        List<Servize> serviceList = servizeService.selectServizeList(query);
        mmp.put("serviceList",serviceList);
        return prefix + "/add";
    }

    /**
     * ????????????supplier
     */
    @RequiresPermissions("data:supplier:add")
    @Log(title = "supplier", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Supplier supplier)
    {
        supplier.setCreateBy(ShiroUtils.getLoginName());
        supplier.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(supplierService.insertSupplier(supplier));
    }

    /**
     * ??????supplier
     */
    @GetMapping("/edit/{supplierId}")
    public String edit(@PathVariable("supplierId") Long supplierId, ModelMap mmap)
    {
        Servize query = new Servize();
        query.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        List<Servize> serviceList = servizeService.selectServizeList(query);

        Supplier supplier = supplierService.selectSupplierById(supplierId);
        mmap.put("supplier", supplier);
        List<CompanyService> companyServices = companyServiceService.selectCompanyServiceBySupplierId(supplierId);
        mmap.put("companyServices", companyServices);
        for (Servize servize : serviceList) {
            for (CompanyService companyService : companyServices) {
                if(servize.getServiceId().equals(companyService.getServiceId())){
                    servize.setSelected(true);
                }
            }
        }
        mmap.put("serviceList",serviceList);

        String attachmentIds = supplier.getAttachmentIds();
        if(StringUtils.isNotEmpty(attachmentIds)){
            String[] attachmentIdStrArray = attachmentIds.split(",");
           long [] attachmentIdArray =
                    Arrays.asList(attachmentIdStrArray).stream().mapToLong(Long::parseLong).toArray();
            List<Attachment> attachments = attachmentService.selectAttachmentByIds(attachmentIdArray);
            if(CollectionUtils.isNotEmpty(attachments)){
                mmap.put("attachments",attachments);
            }
        }
        return prefix + "/edit";
    }

    /**
     * ????????????supplier
     */
    @RequiresPermissions("data:supplier:edit")
    @Log(title = "supplier", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Supplier supplier)
    {
        supplier.setUpdateBy(ShiroUtils.getLoginName());
        supplier.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(supplierService.updateSupplier(supplier));
    }

    /**
     * ??????supplier
     */
    @RequiresPermissions("data:supplier:remove")
    @Log(title = "supplier", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(supplierService.deleteSupplierByIds(ids));
    }

    /**
     * ??????SupplierName??????
     */
    @PostMapping("/checkSupplierNameUnique")
    @ResponseBody
    public String checkSupplierNameUnique(Supplier supplier)
    {
        supplier.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return supplierService.checkSupplierNameUnique(supplier);
    }

    /**
     * ????????????
     */
    @Log(title = "??????supplier??????", businessType = BusinessType.DELETE)
    @RequiresPermissions("data:supplier:edit")
    @PostMapping("/attachment/remove")
    @ResponseBody
    public AjaxResult remove(Supplier supplier)
    {
        supplier.setCreateBy(ShiroUtils.getLoginName());
        supplier.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return  AjaxResult.success();
    }
}
