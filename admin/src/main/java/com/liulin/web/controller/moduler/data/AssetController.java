package com.liulin.web.controller.moduler.data;

import com.liulin.common.annotation.Log;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.core.domain.entity.SysDept;
import com.liulin.common.core.page.TableDataInfo;
import com.liulin.common.enums.BusinessType;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.system.domain.Asset;
import com.liulin.system.domain.Attachment;
import com.liulin.system.domain.BuildingLevel;
import com.liulin.system.service.IAssetService;
import com.liulin.system.service.IAttachmentService;
import com.liulin.system.service.IBuildingLevelService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * AssetController
 * 
 * @author liulin
 * @date 2021-08-07
 */
@Controller
@RequestMapping("/data/asset")
public class AssetController extends BaseController
{
    private String prefix = "data/asset";

    @Autowired
    private IAssetService assetService;

    @Autowired
    private IBuildingLevelService buildingLevelService;

    @Autowired
    private IAttachmentService attachmentService;

    @RequiresPermissions("data:asset:view")
    @GetMapping()
    public String asset(ModelMap mp)
    {
        Integer multiBuilding = ShiroUtils.getSysUser().getBuilding().getMultiBuilding();
        if(multiBuilding.equals(SysDept.NOT_MULTI_BUILDING)){
            List<BuildingLevel> buildingLevels = buildingLevelService.selectBuildingLevelListByBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
            mp.put("buildingLevels",buildingLevels);
        }
        mp.put("multiBuilding",multiBuilding);
        return prefix + "/asset";
    }

    /**
     * ??????Asset??????
     */
    @RequiresPermissions("data:asset:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Asset asset)
    {
        asset.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        startPage();
        List<Asset> list = assetService.selectAssetList(asset);
        return getDataTable(list);
    }

    /**
     * ??????Asset??????
     */
    @RequiresPermissions("data:asset:export")
    @Log(title = "Asset", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Asset asset)
    {
        asset.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        List<Asset> list = assetService.selectAssetList(asset);
        ExcelUtil<Asset> util = new ExcelUtil<Asset>(Asset.class);
        return util.exportExcel(list, "Asset??????");
    }

    /**
     * ??????Asset
     */
    @GetMapping("/add")
    public String add(ModelMap mp)
    {
        Integer multiBuilding = ShiroUtils.getSysUser().getBuilding().getMultiBuilding();
        if(multiBuilding.equals(SysDept.NOT_MULTI_BUILDING)){
            List<BuildingLevel> buildingLevels = buildingLevelService.selectBuildingLevelListByBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
            mp.put("buildingLevels",buildingLevels);
        }
        mp.put("multiBuilding",multiBuilding);
        return prefix + "/add";
    }

    /**
     * ????????????Asset
     */
    @RequiresPermissions("data:asset:add")
    @Log(title = "Asset", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Asset asset)
    {
        asset.setCreateBy(ShiroUtils.getLoginName());
        asset.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        asset.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(assetService.insertAsset(asset));
    }

    /**
     * ??????Asset
     */
    @GetMapping("/edit/{assetId}")
    public String edit(@PathVariable("assetId") Long assetId, ModelMap mmap)
    {
        Integer multiBuilding = ShiroUtils.getSysUser().getBuilding().getMultiBuilding();
        if(multiBuilding.equals(SysDept.NOT_MULTI_BUILDING)){
            List<BuildingLevel> buildingLevels = buildingLevelService.selectBuildingLevelListByBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
            mmap.put("buildingLevels",buildingLevels);
        }
        mmap.put("multiBuilding",multiBuilding);
        Asset asset = assetService.selectAssetById(assetId);
        mmap.put("asset", asset);
        String attachmentIds = asset.getAttachmentIds();
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
     * ????????????Asset
     */
    @RequiresPermissions("data:asset:edit")
    @Log(title = "Asset", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Asset asset)
    {
        asset.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        asset.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        asset.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(assetService.updateAsset(asset));
    }

    /**
     * ??????Asset
     */
    @RequiresPermissions("data:asset:remove")
    @Log(title = "Asset", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(assetService.deleteAssetByIds(ids));
    }


    /**
     * ??????AssetName??????
     */
    @PostMapping("/checkAssetNameUnique")
    @ResponseBody
    public String checkAssetNameUnique(Asset asset)
    {
        asset.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        return assetService.checkAssetNameUnique(asset);
    }

    /**
     * ????????????
     */
    @Log(title = "??????asset??????", businessType = BusinessType.DELETE)
    @RequiresPermissions("data:asset:edit")
    @PostMapping("/attachment/remove")
    @ResponseBody
    public AjaxResult remove(Asset asset)
    {
        asset.setCreateBy(ShiroUtils.getLoginName());
        asset.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        asset.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return  AjaxResult.success();
    }
}
