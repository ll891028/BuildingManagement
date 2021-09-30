package com.liulin.web.controller.moduler;

import java.util.Date;
import java.util.List;

import com.liulin.common.utils.ShiroUtils;
import com.liulin.system.domain.*;
import com.liulin.system.service.IAssetService;
import com.liulin.system.service.IBuildingLevelService;
import com.liulin.system.service.ISafetyCheckAssetService;
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
import com.liulin.system.service.ISafetyCheckService;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.common.core.page.TableDataInfo;

/**
 * Safety CheckController
 * 
 * @author liulin
 * @date 2021-09-29
 */
@Controller
@RequestMapping("/event/safetyCheck")
public class SafetyCheckController extends BaseController
{
    private String prefix = "event/safetyCheck";

    @Autowired
    private ISafetyCheckService safetyCheckService;

    @Autowired
    private ISafetyCheckAssetService safetyCheckAssetService;

    @Autowired
    private IBuildingLevelService buildingLevelService;

    @Autowired
    private IAssetService assetService;

    @RequiresPermissions("event:safetyCheck:view")
    @GetMapping()
    public String safetyCheck()
    {
        return prefix + "/safetyCheck";
    }

    /**
     * 查询Safety Check列表
     */
    @RequiresPermissions("event:safetyCheck:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SafetyCheck safetyCheck)
    {
        safetyCheck.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        startPage();
        List<SafetyCheck> list = safetyCheckService.selectSafetyCheckList(safetyCheck);
        return getDataTable(list);
    }

    /**
     * 导出Safety Check列表
     */
    @RequiresPermissions("event:safetyCheck:export")
    @Log(title = "Safety Check", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SafetyCheck safetyCheck)
    {
        List<SafetyCheck> list = safetyCheckService.selectSafetyCheckList(safetyCheck);
        ExcelUtil<SafetyCheck> util = new ExcelUtil<SafetyCheck>(SafetyCheck.class);
        return util.exportExcel(list, "Safety Check数据");
    }

    /**
     * 新增Safety Check
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存Safety Check
     */
    @RequiresPermissions("event:safetyCheck:add")
    @Log(title = "Safety Check", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SafetyCheck safetyCheck)
    {
        safetyCheck.setCreateBy(ShiroUtils.getLoginName());
        safetyCheck.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        return toAjax(safetyCheckService.insertSafetyCheck(safetyCheck));
    }

    /**
     * 修改Safety Check
     */
    @GetMapping("/edit/{safetyCheckId}")
    public String edit(@PathVariable("safetyCheckId") Long safetyCheckId, ModelMap mmap)
    {
        SafetyCheck safetyCheck = safetyCheckService.selectSafetyCheckById(safetyCheckId);
        mmap.put("safetyCheck", safetyCheck);

        SafetyCheckAsset assetQuery = new SafetyCheckAsset();
        assetQuery.setSafetyCheckId(safetyCheckId);
        List<SafetyCheckAsset> safetyCheckAssets = safetyCheckAssetService.selectSafetyCheckAssetList(assetQuery);
        mmap.put("safetyCheckAssets",safetyCheckAssets);
        return prefix + "/edit";
    }

    /**
     * 修改保存Safety Check
     */
    @RequiresPermissions("event:safetyCheck:edit")
    @Log(title = "Safety Check", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SafetyCheck safetyCheck)
    {
        safetyCheck.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(safetyCheckService.updateSafetyCheck(safetyCheck));
    }

    /**
     * 删除Safety Check
     */
    @RequiresPermissions("event:safetyCheck:remove")
    @Log(title = "Safety Check", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(safetyCheckService.deleteSafetyCheckByIds(ids));
    }


    @RequiresPermissions("event:safetyCheck:add")
    @GetMapping("/asset")
    public String assetList(ModelMap mmap)
    {
        List<BuildingLevel> buildingLevels = buildingLevelService.selectBuildingLevelListByBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        mmap.put("buildingLevels",buildingLevels);
        return prefix + "/assetList";
    }

    /**
     * 查询Asset列表
     */
    @RequiresPermissions("event:safetyCheck:add")
    @PostMapping("/asset/list")
    @ResponseBody
    public TableDataInfo assetList(Asset asset)
    {
        asset.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        asset.setNeedSaftyCheck(1);
        startPage();
        List<Asset> list = assetService.selectAssetList(asset);
        return getDataTable(list);
    }
}
