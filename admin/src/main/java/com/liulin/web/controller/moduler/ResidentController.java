package com.liulin.web.controller.moduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.liulin.common.core.domain.entity.SysDept;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.system.domain.*;
import com.liulin.system.service.*;
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
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.common.core.page.TableDataInfo;

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

        List<BuildingLevel> buildingLevels = buildingLevelService.selectBuildingLevelListByBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        mp.put("buildingLevels",buildingLevels);
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
        resident.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
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
    public String add(ModelMap mp)
    {
        List<BuildingLevel> buildingLevels = buildingLevelService.selectBuildingLevelListByBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        mp.put("buildingLevels",buildingLevels);
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
        resident.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(residentService.insertResident(resident));
    }

    /**
     * 修改resident
     */
    @GetMapping("/edit/{residentId}")
    public String edit(@PathVariable("residentId") Long residentId, ModelMap mmap)
    {
        List<BuildingLevel> buildingLevels = buildingLevelService.selectBuildingLevelListByBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        mmap.put("buildingLevels",buildingLevels);
        Resident resident = residentService.selectResidentById(residentId);
        mmap.put("resident", resident);
        CarSpace query = new CarSpace();
        query.setResidentId(residentId);
        List<CarSpace> carSpaces = carSpaceService.selectCarSpaceList(query);
        mmap.put("carSpaces", carSpaces);
        String attachmentIds = resident.getAttachmentIds();
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


    /**
     * 校验UnitNumber唯一
     */
    @PostMapping("/checkUnitNumberUnique")
    @ResponseBody
    public String checkUnitNumberUnique(Resident resident)
    {
        resident.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        return residentService.checkUnitNumberUnique(resident);
    }

    /**
     * 校验CarSpaceNumber唯一
     */
    @PostMapping("/checkCarSpaceNumberUnique")
    @ResponseBody
    public String checkCarSpaceNumberUnique(CarSpace carSpace)
    {
        carSpace.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        return carSpaceService.checkCarSpaceNumberUnique(carSpace);
    }

    /**
     * 校验CarPlateNumber唯一
     */
    @PostMapping("/checkCarPlateNumberUnique")
    @ResponseBody
    public String checkCarPlateNumberUnique(CarPlate carPlate)
    {
        carPlate.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        return carPlateService.checkCarPlateNumberUnique(carPlate);
    }

    /**
     * 删除附件
     */
    @Log(title = "删除resident附件", businessType = BusinessType.DELETE)
    @RequiresPermissions("data:resident:remove")
    @PostMapping("/attachment/remove")
    @ResponseBody
    public AjaxResult remove(Resident resident)
    {
        resident.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(residentService.updateAttachment(resident));
    }
}
