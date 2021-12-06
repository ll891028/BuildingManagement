package com.liulin.web.controller.moduler;

import com.liulin.common.annotation.Log;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.core.page.TableDataInfo;
import com.liulin.common.enums.BusinessType;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.system.domain.Attachment;
import com.liulin.system.domain.Defects;
import com.liulin.system.service.IAttachmentService;
import com.liulin.system.service.IDefectsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Defects RegisterController
 * 
 * @author liulin
 * @date 2021-10-15
 */
@Controller
@RequestMapping("/event/defects")
public class DefectsController extends BaseController
{
    private String prefix = "event/defects";

    @Autowired
    private IDefectsService defectsService;

    @Autowired
    private IAttachmentService attachmentService;

    @RequiresPermissions("event:defects:view")
    @GetMapping()
    public String defects(ModelMap mmap)
    {
        mmap.put("buildingId",ShiroUtils.getSysUser().getBuilding().getDeptId());
        return prefix + "/defects";
    }

    /**
     * 查询Defects Register列表
     */
    @RequiresPermissions("event:defects:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Defects defects)
    {
//        defects.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        startPage();
        List<Defects> list = defectsService.selectDefectsList(defects);
        return getDataTable(list);
    }

    /**
     * 导出Defects Register列表
     */
    @RequiresPermissions("event:defects:export")
    @Log(title = "Defects Register", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Defects defects)
    {
        List<Defects> list = defectsService.selectDefectsList(defects);
        ExcelUtil<Defects> util = new ExcelUtil<Defects>(Defects.class);
        return util.exportExcel(list, "Defects Register数据");
    }

    /**
     * 新增Defects Register
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存Defects Register
     */
    @RequiresPermissions("event:defects:add")
    @Log(title = "Defects Register", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Defects defects)
    {
        defects.setCreateBy(ShiroUtils.getLoginName());
        defects.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        defects.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(defectsService.insertDefects(defects));
    }

    /**
     * 修改Defects Register
     */
    @GetMapping("/edit/{defectId}")
    public String edit(@PathVariable("defectId") Long defectId, ModelMap mmap)
    {
        Defects defects = defectsService.selectDefectsById(defectId);
        mmap.put("defects", defects);

        String attachmentIds = defects.getAttachmentIds();
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
     * 修改保存Defects Register
     */
    @RequiresPermissions("event:defects:edit")
    @Log(title = "Defects Register", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Defects defects)
    {

        defects.setUpdateBy(ShiroUtils.getLoginName());
        defects.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        defects.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(defectsService.updateDefects(defects));
    }

    /**
     * 删除Defects Register
     */
    @RequiresPermissions("event:defects:remove")
    @Log(title = "Defects Register", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(defectsService.deleteDefectsByIds(ids));
    }

    /**
     * 删除附件
     */
    @Log(title = "删除defects附件", businessType = BusinessType.DELETE)
    @RequiresPermissions("event:defects:remove")
    @PostMapping("/attachment/remove")
    @ResponseBody
    public AjaxResult remove(Defects defects)
    {
        defects.setCreateBy(ShiroUtils.getLoginName());
        defects.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        defects.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(defectsService.updateDefectsAttachment(defects));
    }
}
