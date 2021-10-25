package com.liulin.web.controller.moduler;

import java.util.Arrays;
import java.util.List;

import com.liulin.common.core.domain.entity.SysDept;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.system.domain.Attachment;
import com.liulin.system.domain.BuildingLevel;
import com.liulin.system.domain.Defects;
import com.liulin.system.service.IAttachmentService;
import com.liulin.system.service.IBuildingLevelService;
import org.apache.commons.collections.CollectionUtils;
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
import com.liulin.system.domain.Asset;
import com.liulin.system.service.IAssetService;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.common.core.page.TableDataInfo;

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
        List<BuildingLevel> buildingLevels = buildingLevelService.selectBuildingLevelListByBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        mp.put("buildingLevels",buildingLevels);
        return prefix + "/asset";
    }

    /**
     * 查询Asset列表
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
     * 导出Asset列表
     */
    @RequiresPermissions("data:asset:export")
    @Log(title = "Asset", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Asset asset)
    {
        List<Asset> list = assetService.selectAssetList(asset);
        ExcelUtil<Asset> util = new ExcelUtil<Asset>(Asset.class);
        return util.exportExcel(list, "Asset数据");
    }

    /**
     * 新增Asset
     */
    @GetMapping("/add")
    public String add(ModelMap mp)
    {
        List<BuildingLevel> buildingLevels = buildingLevelService.selectBuildingLevelListByBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        mp.put("buildingLevels",buildingLevels);
        return prefix + "/add";
    }

    /**
     * 新增保存Asset
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
     * 修改Asset
     */
    @GetMapping("/edit/{assetId}")
    public String edit(@PathVariable("assetId") Long assetId, ModelMap mmap)
    {
        List<BuildingLevel> buildingLevels = buildingLevelService.selectBuildingLevelListByBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        mmap.put("buildingLevels",buildingLevels);
        Asset asset = assetService.selectAssetById(assetId);
        mmap.put("asset", asset);
        String attachmentIds = asset.getAttachmentIds();
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
     * 修改保存Asset
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
     * 删除Asset
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
     * 校验AssetName唯一
     */
    @PostMapping("/checkAssetNameUnique")
    @ResponseBody
    public String checkAssetNameUnique(Asset asset)
    {
        asset.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        return assetService.checkAssetNameUnique(asset);
    }

    /**
     * 删除附件
     */
    @Log(title = "删除asset附件", businessType = BusinessType.DELETE)
    @RequiresPermissions("data:asset:remove")
    @PostMapping("/attachment/remove")
    @ResponseBody
    public AjaxResult remove(Asset asset)
    {
        asset.setCreateBy(ShiroUtils.getLoginName());
        asset.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        asset.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(assetService.updateAttachment(asset));
    }
}
