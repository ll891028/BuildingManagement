package com.liulin.web.controller.moduler.data;

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
import com.liulin.system.domain.CarPlate;
import com.liulin.system.service.ICarPlateService;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.common.core.page.TableDataInfo;

/**
 * PlateController
 * 
 * @author liulin
 * @date 2021-08-17
 */
@Controller
@RequestMapping("/data/carPlate")
public class CarPlateController extends BaseController
{
    private String prefix = "data/carPlate";

    @Autowired
    private ICarPlateService carPlateService;

    @RequiresPermissions("data:carPlate:view")
    @GetMapping()
    public String carPlate()
    {
        return prefix + "/carPlate";
    }

    /**
     * 查询Plate列表
     */
    @RequiresPermissions("data:carPlate:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CarPlate carPlate)
    {
        startPage();
        List<CarPlate> list = carPlateService.selectCarPlateList(carPlate);
        return getDataTable(list);
    }

    /**
     * 导出Plate列表
     */
    @RequiresPermissions("data:carPlate:export")
    @Log(title = "Plate", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CarPlate carPlate)
    {
        List<CarPlate> list = carPlateService.selectCarPlateList(carPlate);
        ExcelUtil<CarPlate> util = new ExcelUtil<CarPlate>(CarPlate.class);
        return util.exportExcel(list, "Plate数据");
    }

    /**
     * 新增Plate
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存Plate
     */
    @RequiresPermissions("data:carPlate:add")
    @Log(title = "Plate", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CarPlate carPlate)
    {
        return toAjax(carPlateService.insertCarPlate(carPlate));
    }

    /**
     * 修改Plate
     */
    @GetMapping("/edit/{carPlateId}")
    public String edit(@PathVariable("carPlateId") Long carPlateId, ModelMap mmap)
    {
        CarPlate carPlate = carPlateService.selectCarPlateById(carPlateId);
        mmap.put("carPlate", carPlate);
        return prefix + "/edit";
    }

    /**
     * 修改保存Plate
     */
    @RequiresPermissions("data:carPlate:edit")
    @Log(title = "Plate", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CarPlate carPlate)
    {
        return toAjax(carPlateService.updateCarPlate(carPlate));
    }

    /**
     * 删除Plate
     */
    @RequiresPermissions("data:carPlate:remove")
    @Log(title = "Plate", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(carPlateService.deleteCarPlateByIds(ids));
    }
}
