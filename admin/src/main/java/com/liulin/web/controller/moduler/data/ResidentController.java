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
import com.liulin.system.domain.*;
import com.liulin.system.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * residentController
 * 
 * @author liulin
 * @date 2021-08-06
 */
@Controller
@RequestMapping("/data/resident")
public class ResidentController extends BaseController
{
    private String prefix = "data/resident";

    @Autowired
    private IResidentService residentService;

    @Autowired
    private IBuildingLevelService buildingLevelService;

    @Autowired
    private ICarSpaceService carSpaceService;

    @Autowired
    private ICarPlateService carPlateService;

    @Autowired
    private IAttachmentService attachmentService;

    @RequiresPermissions("data:resident:view")
    @GetMapping()
    public String resident(ModelMap mp)
    {
        Integer multiBuilding = ShiroUtils.getSysUser().getBuilding().getMultiBuilding();
        if(multiBuilding.equals(SysDept.NOT_MULTI_BUILDING)){
            List<BuildingLevel> buildingLevels = buildingLevelService.selectBuildingLevelListByBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
            mp.put("buildingLevels",buildingLevels);
        }
        mp.put("multiBuilding",multiBuilding);
        return prefix + "/resident";
    }

    /**
     * ??????resident??????
     */
    @RequiresPermissions("data:resident:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Resident resident)
    {
        resident.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        startPage();
        List<Resident> list = residentService.selectResidentList(resident);
        return getDataTable(list);
    }

    /**
     * ??????resident??????
     */
    @RequiresPermissions("data:resident:export")
    @Log(title = "resident", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Resident resident)
    {
        resident.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        List<Resident> list = residentService.selectResidentList(resident);
        ExcelUtil<Resident> util = new ExcelUtil<Resident>(Resident.class);
        return util.exportExcel(list, "resident??????");
    }

    /**
     * ??????resident
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
     * ????????????resident
     */
    @RequiresPermissions("data:resident:add")
    @Log(title = "resident", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Resident resident)
    {
        resident.setCreateBy(ShiroUtils.getLoginName());
        resident.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        resident.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(residentService.insertResident(resident));
    }

    /**
     * ??????resident
     */
    @GetMapping("/edit/{residentId}")
    public String edit(@PathVariable("residentId") Long residentId, ModelMap mmap)
    {
        Integer multiBuilding = ShiroUtils.getSysUser().getBuilding().getMultiBuilding();
        if(multiBuilding.equals(SysDept.NOT_MULTI_BUILDING)){
            List<BuildingLevel> buildingLevels = buildingLevelService.selectBuildingLevelListByBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
            mmap.put("buildingLevels",buildingLevels);
        }
        mmap.put("multiBuilding",multiBuilding);
        Resident resident = residentService.selectResidentById(residentId);
        mmap.put("resident", resident);
        CarSpace query = new CarSpace();
        query.setResidentId(residentId);
        List<CarSpace> carSpaces = carSpaceService.selectCarSpaceList(query);
        mmap.put("carSpaces", carSpaces);
        String attachmentIds = resident.getAttachmentIds();
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
     * ????????????resident
     */
    @RequiresPermissions("data:resident:edit")
    @Log(title = "resident", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Resident resident)
    {
        resident.setUpdateBy(ShiroUtils.getLoginName());
        resident.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        resident.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(residentService.updateResident(resident));
    }

    /**
     * ??????resident
     */
    @RequiresPermissions("data:resident:remove")
    @Log(title = "resident", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(residentService.deleteResidentByIds(ids));
    }


    /**
     * ??????UnitNumber??????
     */
    @PostMapping("/checkUnitNumberUnique")
    @ResponseBody
    public String checkUnitNumberUnique(Resident resident)
    {
        resident.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        return residentService.checkUnitNumberUnique(resident);
    }

    /**
     * ??????CarSpaceNumber??????
     */
    @PostMapping("/checkCarSpaceNumberUnique")
    @ResponseBody
    public String checkCarSpaceNumberUnique(CarSpace carSpace)
    {
        carSpace.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        return carSpaceService.checkCarSpaceNumberUnique(carSpace);
    }

    /**
     * ??????CarPlateNumber??????
     */
    @PostMapping("/checkCarPlateNumberUnique")
    @ResponseBody
    public String checkCarPlateNumberUnique(CarPlate carPlate)
    {
        carPlate.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        return carPlateService.checkCarPlateNumberUnique(carPlate);
    }

    /**
     * ????????????
     */
    @Log(title = "??????resident??????", businessType = BusinessType.DELETE)
    @RequiresPermissions("data:resident:edit")
    @PostMapping("/attachment/remove")
    @ResponseBody
    public AjaxResult remove(Resident resident)
    {
        resident.setCreateBy(ShiroUtils.getLoginName());
        resident.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        resident.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return  AjaxResult.success();
    }
}
