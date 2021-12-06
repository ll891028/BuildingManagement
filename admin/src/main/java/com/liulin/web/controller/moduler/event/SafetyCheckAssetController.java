package com.liulin.web.controller.moduler.event;

import com.liulin.common.annotation.Log;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.core.page.TableDataInfo;
import com.liulin.common.enums.BusinessType;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.system.domain.SafetyCheckAsset;
import com.liulin.system.service.ISafetyCheckAssetService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Safety Check AssetController
 * 
 * @author liulin
 * @date 2021-09-29
 */
@Controller
@RequestMapping("/event/safetyCheckAsset")
public class SafetyCheckAssetController extends BaseController
{
    private String prefix = "event/safetyCheckAsset";

    @Autowired
    private ISafetyCheckAssetService safetyCheckAssetService;

    @RequiresPermissions("event:safetyCheckAsset:view")
    @GetMapping("/{safetyCheckId}")
    public String safetyCheckAsset(@PathVariable Long safetyCheckId)
    {
        return prefix + "/safetyCheckAsset";
    }

    /**
     * 查询Safety Check Asset列表
     */
    @RequiresPermissions("event:safetyCheckAsset:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SafetyCheckAsset safetyCheckAsset)
    {
        startPage();
        List<SafetyCheckAsset> list = safetyCheckAssetService.selectSafetyCheckAssetList(safetyCheckAsset);
        return getDataTable(list);
    }

    /**
     * 导出Safety Check Asset列表
     */
    @RequiresPermissions("event:safetyCheckAsset:export")
    @Log(title = "Safety Check Asset", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SafetyCheckAsset safetyCheckAsset)
    {
        List<SafetyCheckAsset> list = safetyCheckAssetService.selectSafetyCheckAssetList(safetyCheckAsset);
        ExcelUtil<SafetyCheckAsset> util = new ExcelUtil<SafetyCheckAsset>(SafetyCheckAsset.class);
        return util.exportExcel(list, "Safety Check Asset数据");
    }

    /**
     * 新增Safety Check Asset
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存Safety Check Asset
     */
    @RequiresPermissions("event:safetyCheckAsset:add")
    @Log(title = "Safety Check Asset", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SafetyCheckAsset safetyCheckAsset)
    {
        return toAjax(safetyCheckAssetService.insertSafetyCheckAsset(safetyCheckAsset));
    }

    /**
     * 修改Safety Check Asset
     */
    @GetMapping("/edit/{safetyCheckAssetId}")
    public String edit(@PathVariable("safetyCheckAssetId") Long safetyCheckAssetId, ModelMap mmap)
    {
        SafetyCheckAsset safetyCheckAsset = safetyCheckAssetService.selectSafetyCheckAssetById(safetyCheckAssetId);
        mmap.put("safetyCheckAsset", safetyCheckAsset);
        return prefix + "/edit";
    }

    /**
     * 修改保存Safety Check Asset
     */
    @RequiresPermissions("event:safetyCheckAsset:edit")
    @Log(title = "Safety Check Asset", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SafetyCheckAsset safetyCheckAsset)
    {
        return toAjax(safetyCheckAssetService.updateSafetyCheckAsset(safetyCheckAsset));
    }

    @RequiresPermissions("event:safetyCheckAsset:edit")
    @Log(title = "changeGoodCondition", businessType = BusinessType.UPDATE)
    @PostMapping("/changeGoodCondition")
    @ResponseBody
    public AjaxResult changeGoodCondition(SafetyCheckAsset safetyCheckAsset)
    {
        return toAjax(safetyCheckAssetService.updateSafetyCheckAsset(safetyCheckAsset));
    }

    /**
     * 删除Safety Check Asset
     */
    @RequiresPermissions("event:safetyCheckAsset:remove")
    @Log(title = "Safety Check Asset", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(safetyCheckAssetService.deleteSafetyCheckAssetByIds(ids));
    }

    /**
     * 删除asset附件
     */
    @Log(title = "删除asset附件", businessType = BusinessType.DELETE)
    @RequiresPermissions("event:safetyCheckAsset:remove")
    @PostMapping("/attachment/remove")
    @ResponseBody
    public AjaxResult attachRemove(SafetyCheckAsset safetyCheckAsset)
    {
        safetyCheckAsset.setCreateBy(ShiroUtils.getLoginName());
        safetyCheckAsset.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        safetyCheckAsset.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(safetyCheckAssetService.updateAssetAttachment(safetyCheckAsset));
    }
}
