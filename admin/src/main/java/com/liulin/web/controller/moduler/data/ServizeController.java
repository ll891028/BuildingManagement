package com.liulin.web.controller.moduler.data;

import java.util.Arrays;
import java.util.List;

import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.system.domain.Asset;
import com.liulin.system.domain.Attachment;
import com.liulin.system.domain.Resident;
import com.liulin.system.service.IAttachmentService;
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
import com.liulin.system.domain.Servize;
import com.liulin.system.service.IServizeService;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.utils.poi.ExcelUtil;
import com.liulin.common.core.page.TableDataInfo;

/**
 * serviceController
 * 
 * @author liulin
 * @date 2021-08-09
 */
@Controller
@RequestMapping("/data/service")
public class ServizeController extends BaseController
{
    private String prefix = "data/service";

    @Autowired
    private IServizeService servizeService;

    @Autowired
    private IAttachmentService attachmentService;

    @RequiresPermissions("data:service:view")
    @GetMapping()
    public String service()
    {
        return prefix + "/service";
    }

    /**
     * 查询service列表
     */
    @RequiresPermissions("data:service:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Servize servize)
    {
        servize.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        startPage();
        List<Servize> list = servizeService.selectServizeList(servize);
        return getDataTable(list);
    }

    /**
     * 导出service列表
     */
    @RequiresPermissions("data:service:export")
    @Log(title = "service", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Servize servize)
    {
        List<Servize> list = servizeService.selectServizeList(servize);
        ExcelUtil<Servize> util = new ExcelUtil<Servize>(Servize.class);
        return util.exportExcel(list, "service数据");
    }

    /**
     * 新增service
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存service
     */
    @RequiresPermissions("data:service:add")
    @Log(title = "service", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Servize servize)
    {
        servize.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(servizeService.insertServize(servize));
    }

    /**
     * 修改service
     */
    @GetMapping("/edit/{serviceId}")
    public String edit(@PathVariable("serviceId") Long serviceId, ModelMap mmap)
    {
        Servize servize = servizeService.selectServizeById(serviceId);
        mmap.put("servize", servize);

        String attachmentIds = servize.getAttachmentIds();
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
     * 修改保存service
     */
    @RequiresPermissions("data:service:edit")
    @Log(title = "service", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Servize servize)
    {
        servize.setUpdateBy(ShiroUtils.getLoginName());
        servize.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(servizeService.updateServize(servize));
    }

    /**
     * 删除service
     */
    @RequiresPermissions("data:service:remove")
    @Log(title = "service", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(servizeService.deleteServizeByIds(ids));
    }

    /**
     * 校验ServiceName唯一
     */
    @PostMapping("/checkServiceNameUnique")
    @ResponseBody
    public String checkServiceNameUnique(Servize servize)
    {
        return servizeService.checkServiceNameUnique(ShiroUtils.getSysUser().getCompany().getDeptId(),
                servize.getServiceName(),servize.getServiceId());
    }

    /**
     * 删除附件
     */
    @Log(title = "删除servize附件", businessType = BusinessType.DELETE)
    @RequiresPermissions("data:service:remove")
    @PostMapping("/attachment/remove")
    @ResponseBody
    public AjaxResult remove(Servize servize)
    {
        servize.setCreateBy(ShiroUtils.getLoginName());
        servize.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return  AjaxResult.success();
    }
}
