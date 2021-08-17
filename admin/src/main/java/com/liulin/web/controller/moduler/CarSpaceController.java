package com.liulin.web.controller.moduler;

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
import com.liulin.system.domain.CarSpace;
import com.liulin.system.service.ICarSpaceService;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.common.core.page.TableDataInfo;

/**
 * CarSpaceController
 * 
 * @author liulin
 * @date 2021-08-17
 */
@Controller
@RequestMapping("/data/carSpace")
public class CarSpaceController extends BaseController
{
    private String prefix = "data/carSpace";

    @Autowired
    private ICarSpaceService carSpaceService;

    @RequiresPermissions("data:carSpace:view")
    @GetMapping()
    public String carSpace()
    {
        return prefix + "/carSpace";
    }

    /**
     * 查询CarSpace列表
     */
    @RequiresPermissions("data:carSpace:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CarSpace carSpace)
    {
        startPage();
        List<CarSpace> list = carSpaceService.selectCarSpaceList(carSpace);
        return getDataTable(list);
    }

    /**
     * 导出CarSpace列表
     */
    @RequiresPermissions("data:carSpace:export")
    @Log(title = "CarSpace", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CarSpace carSpace)
    {
        List<CarSpace> list = carSpaceService.selectCarSpaceList(carSpace);
        ExcelUtil<CarSpace> util = new ExcelUtil<CarSpace>(CarSpace.class);
        return util.exportExcel(list, "CarSpace数据");
    }

    /**
     * 新增CarSpace
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存CarSpace
     */
    @RequiresPermissions("data:carSpace:add")
    @Log(title = "CarSpace", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CarSpace carSpace)
    {
        return toAjax(carSpaceService.insertCarSpace(carSpace));
    }

    /**
     * 修改CarSpace
     */
    @GetMapping("/edit/{carSpaceId}")
    public String edit(@PathVariable("carSpaceId") Long carSpaceId, ModelMap mmap)
    {
        CarSpace carSpace = carSpaceService.selectCarSpaceById(carSpaceId);
        mmap.put("carSpace", carSpace);
        return prefix + "/edit";
    }

    /**
     * 修改保存CarSpace
     */
    @RequiresPermissions("data:carSpace:edit")
    @Log(title = "CarSpace", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CarSpace carSpace)
    {
        return toAjax(carSpaceService.updateCarSpace(carSpace));
    }

    /**
     * 删除CarSpace
     */
    @RequiresPermissions("data:carSpace:remove")
    @Log(title = "CarSpace", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(carSpaceService.deleteCarSpaceByIds(ids));
    }
}
