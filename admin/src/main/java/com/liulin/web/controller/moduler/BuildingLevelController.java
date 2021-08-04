package com.liulin.web.controller.moduler;

import java.util.ArrayList;
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
import com.liulin.system.domain.BuildingLevel;
import com.liulin.system.service.IBuildingLevelService;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.common.core.page.TableDataInfo;

/**
 * building_levelController
 *
 * @author liulin
 * @date 2021-08-04
 */
@Controller
@RequestMapping("/data/level")
public class BuildingLevelController extends BaseController {
    private String prefix = "data/level";

    @Autowired
    private IBuildingLevelService buildingLevelService;

    @RequiresPermissions("data:level:view")
    @GetMapping("/{buildingId}")
    public String level(@PathVariable(required = false) Long buildingId, ModelMap mmap) {
        mmap.put("buildingId", buildingId);
        return prefix + "/level";
    }

    /**
     * 查询building_level列表
     */
    @RequiresPermissions("data:level:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BuildingLevel buildingLevel) {
        startPage();
        List<BuildingLevel> list = buildingLevelService.selectBuildingLevelList(buildingLevel);
        return getDataTable(list);
    }

    @GetMapping("/generateBuildingLevel/{levels}/{ifGroundFloor}/{basements}")
    public String generateBuildingLevel(@PathVariable Integer levels, @PathVariable Integer ifGroundFloor,
                                        @PathVariable Integer basements, ModelMap mmap) {
        List<BuildingLevel> datas = new ArrayList<>();

        Long levelCount = Long.valueOf(String.valueOf(levels));
        if (levels != null && levels != 0) {
            for (Integer i = levels; i > 0; i--) {
                datas.add(new BuildingLevel("Level " + (i), --levelCount));
            }
        }
        if (ifGroundFloor != null && ifGroundFloor == 1) {
            //有groundFloor
            datas.add(new BuildingLevel("Ground Floor", levelCount));
            levelCount++;
        }
        Long basementsCount = 0L;
        if (basements != null && basements != 0) {
            for (Integer i = 0; i <basements; i++) {
                datas.add(new BuildingLevel("Basement " + (i+1), ++basementsCount));
            }
        }
        mmap.put("levelData",datas);
        return prefix + "/generateBuildingLevel";
    }

    /**
     * 导出building_level列表
     */
    @RequiresPermissions("data:level:export")
    @Log(title = "building_level", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BuildingLevel buildingLevel) {
        List<BuildingLevel> list = buildingLevelService.selectBuildingLevelList(buildingLevel);
        ExcelUtil<BuildingLevel> util = new ExcelUtil<BuildingLevel>(BuildingLevel.class);
        return util.exportExcel(list, "building_level数据");
    }

    /**
     * 新增building_level
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存building_level
     */
    @RequiresPermissions("data:level:add")
    @Log(title = "building_level", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BuildingLevel buildingLevel) {
        return toAjax(buildingLevelService.insertBuildingLevel(buildingLevel));
    }

    /**
     * 修改building_level
     */
    @GetMapping("/edit/{levelId}")
    public String edit(@PathVariable("levelId") Long levelId, ModelMap mmap) {
        BuildingLevel buildingLevel = buildingLevelService.selectBuildingLevelById(levelId);
        mmap.put("buildingLevel", buildingLevel);
        return prefix + "/edit";
    }

    /**
     * 修改保存building_level
     */
    @RequiresPermissions("data:level:edit")
    @Log(title = "building_level", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BuildingLevel buildingLevel) {
        return toAjax(buildingLevelService.updateBuildingLevel(buildingLevel));
    }

    /**
     * 删除building_level
     */
    @RequiresPermissions("data:level:remove")
    @Log(title = "building_level", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(buildingLevelService.deleteBuildingLevelByIds(ids));
    }
}
