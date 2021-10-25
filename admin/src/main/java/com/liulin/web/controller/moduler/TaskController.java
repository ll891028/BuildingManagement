package com.liulin.web.controller.moduler;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
 * TaskController
 * 
 * @author liulin
 * @date 2021-08-19
 */
@Controller
@RequestMapping("/event/task")
public class TaskController extends BaseController
{
    private String prefix = "event/task";

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IServizeService servizeService;

    @Autowired
    private ITaskAssetService taskAssetService;

    @Autowired
    private IAssetService assetService;

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private ITaskQuoteService taskQuoteService;

    @Autowired
    private IBuildingLevelService buildingLevelService;

    @Autowired
    private IAttachmentService attachmentService;

    @RequiresPermissions("event:task:view")
    @GetMapping(value = {"/{taskType}",""})
    public String task(@PathVariable(required = false,value = "taskType") Integer taskType, ModelMap mmp)
    {

        Servize query = new Servize();
        query.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        List<Servize> servizes = servizeService.selectServizeList(query);
        mmp.put("servizes",servizes);
        mmp.put("taskType",taskType==null?1:taskType);
        return prefix + "/task";
    }

    /**
     * 查询Task列表
     */
    @RequiresPermissions("event:task:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Task task)
    {
        task.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        startPage();
        List<Task> list = taskService.selectTaskList(task);
        return getDataTable(list);
    }

    /**
     * 导出Task列表
     */
    @RequiresPermissions("event:task:export")
    @Log(title = "Task", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Task task)
    {
        List<Task> list = taskService.selectTaskList(task);
        ExcelUtil<Task> util = new ExcelUtil<Task>(Task.class);
        return util.exportExcel(list, "Task数据");
    }

    /**
     * 新增Task
     */
    @GetMapping(value = {"/add","/add/{taskType}"})
    public String add(@PathVariable(required = false,value = "taskType")Integer taskType, ModelMap mmp)
    {

        Servize query = new Servize();
        query.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        List<Servize> servizes = servizeService.selectServizeList(query);
        mmp.put("servizes",servizes);
        mmp.put("taskType",taskType==null?1:taskType);
        return prefix + "/add";
    }

    /**
     * 新增保存Task
     */
    @RequiresPermissions("event:task:add")
    @Log(title = "Task", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Task task)
    {
        task.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        task.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        task.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(taskService.insertTask(task));
    }

    /**
     * 修改Task
     */
    @GetMapping(value = "/edit/{taskId}")
    public String edit(@PathVariable("taskId") Long taskId, ModelMap mmap)
    {
        Servize query = new Servize();
        query.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        List<Servize> servizes = servizeService.selectServizeList(query);
        mmap.put("servizes",servizes);

        Task task = taskService.selectTaskById(taskId);
        mmap.put("task", task);

        TaskAsset assetQuery = new TaskAsset();
        assetQuery.setTaskId(taskId);
        List<TaskAsset> taskAssets = taskAssetService.selectTaskAssetList(assetQuery);
        mmap.put("taskAssets",taskAssets);

        TaskQuote quoteQuery = new TaskQuote();
        quoteQuery.setTaskId(taskId);
        List<TaskQuote> taskQuotes = taskQuoteService.selectTaskQuoteList(quoteQuery);
        mmap.put("taskQuotes",taskQuotes);

        String attachmentIds = task.getAttachmentIds();
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
     * 修改保存Task
     */
    @RequiresPermissions("event:task:edit")
    @Log(title = "Task", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Task task)
    {
        task.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        task.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(taskService.updateTask(task));
    }

    /**
     * 删除Task
     */
    @RequiresPermissions("event:task:remove")
    @Log(title = "Task", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(taskService.deleteTaskByIds(ids));
    }


    @RequiresPermissions("event:task:view")
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
    @RequiresPermissions("event:task:list")
    @PostMapping("/asset/list")
    @ResponseBody
    public TableDataInfo assetList(Asset asset)
    {
        asset.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        startPage();
        List<Asset> list = assetService.selectAssetList(asset);
        return getDataTable(list);
    }

    @RequiresPermissions("event:task:view")
    @GetMapping("/selectedAsset")
    public String selectedAssetList()
    {
        return prefix + "/selectedAsset";
    }

    /**
     * 查询selectedTaskAsset列表
     */
    @RequiresPermissions("event:task:list")
    @PostMapping("/selectedAsset/list")
    @ResponseBody
    public TableDataInfo selectedAssetList(TaskAsset taskAsset)
    {
        startPage();
        List<TaskAsset> list = taskAssetService.selectTaskAssetList(taskAsset);
        return getDataTable(list);
    }

    /**
     * 删除TaskAsset
     */
    @RequiresPermissions("event:task:remove")
    @Log(title = "TaskAsset", businessType = BusinessType.DELETE)
    @PostMapping( "/removeTaskAsset")
    @ResponseBody
    public AjaxResult removeTaskAsset(String ids)
    {

        return toAjax(taskAssetService.deleteTaskAssetByIds(ids));
    }


    @RequiresPermissions("event:task:view")
    @GetMapping("/selectSupplier/{serviceId}")
    public String selectSupplierList(@PathVariable Long serviceId,ModelMap mmp)
    {
        mmp.put("serviceId",serviceId);
        return prefix + "/orderSupplierList";
    }

    /**
     * 查询selectSupplier列表
     */
    @RequiresPermissions("event:task:list")
    @PostMapping("/selectSupplierList/list")
    @ResponseBody
    public TableDataInfo selectSupplierList(Supplier supplier)
    {
        supplier.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        startPage();
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        return getDataTable(list);
    }

    @RequiresPermissions("event:task:view")
    @GetMapping("/selectQuoteSupplier/{serviceId}")
    public String selectQuoteSupplier(@PathVariable Long serviceId,ModelMap mmp)
    {
        mmp.put("serviceId",serviceId);
        return prefix + "/selectQuoteSupplierList";
    }

    /**
     * 查询selectSupplier列表
     */
    @RequiresPermissions("event:task:list")
    @PostMapping("/selectQuoteSupplier/list")
    @ResponseBody
    public TableDataInfo selectQuoteSupplier(TaskQuote taskQuote)
    {
        startPage();
        List<TaskQuote> list = taskQuoteService.selectTaskQuoteList(taskQuote);
        return getDataTable(list);
    }

    @RequiresPermissions("event:task:view")
    @GetMapping("/quoteSupplierList/{serviceId}")
    public String quoteSupplierList(@PathVariable Long serviceId,ModelMap mmp)
    {
        mmp.put("serviceId",serviceId);
        return prefix + "/quoteSupplierList";
    }

    /**
     * 查询selectSupplier列表
     */
    @RequiresPermissions("event:task:list")
    @PostMapping("/quoteSupplierList/list")
    @ResponseBody
    public TableDataInfo quoteSupplierList(Supplier supplier)
    {
        supplier.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        startPage();
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        return getDataTable(list);
    }

    @RequiresPermissions("event:task:view")
    @GetMapping("/quotePriceList/{taskId}")
    public String quoteList(@PathVariable Long taskId,ModelMap mmp)
    {
        mmp.put("taskId",taskId);
        return prefix + "/quoteList";
    }

    /**
     * 查询selectSupplier列表
     */
    @RequiresPermissions("event:task:list")
    @PostMapping("/quoteList/list")
    @ResponseBody
    public TableDataInfo quoteList(TaskQuote taskQuote)
    {
        startPage();
        TaskQuote quoteQuery = new TaskQuote();
        quoteQuery.setTaskId(taskQuote.getTaskId());
        List<TaskQuote> taskQuotes = taskQuoteService.selectTaskQuoteList(quoteQuery);
        return getDataTable(taskQuotes);
    }

    /**
     * 修改保存QuorePrice
     */
    @RequiresPermissions("event:task:edit")
    @Log(title = "Task", businessType = BusinessType.UPDATE)
    @PostMapping("/saveQuotePrice")
    @ResponseBody
    public AjaxResult saveQuotePrice(TaskQuote taskQuote)
    {
        return toAjax(taskQuoteService.updateTaskQuote(taskQuote));
    }

    /**
     * 预览Task
     */
    @GetMapping(value = "/detail/{taskId}")
    public String detail(@PathVariable("taskId") Long taskId, ModelMap mmap)
    {
        Servize query = new Servize();
        query.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        List<Servize> servizes = servizeService.selectServizeList(query);
        mmap.put("servizes",servizes);

        Task task = taskService.selectTaskById(taskId);
        mmap.put("task", task);

        TaskAsset assetQuery = new TaskAsset();
        assetQuery.setTaskId(taskId);
        List<TaskAsset> taskAssets = taskAssetService.selectTaskAssetList(assetQuery);
        mmap.put("taskAssets",taskAssets);

        TaskQuote quoteQuery = new TaskQuote();
        quoteQuery.setTaskId(taskId);
        List<TaskQuote> taskQuotes = taskQuoteService.selectTaskQuoteList(quoteQuery);
        mmap.put("taskQuotes",taskQuotes);
        return prefix + "/detail";
    }

    /**
     * 删除附件
     */
    @Log(title = "删除task附件", businessType = BusinessType.DELETE)
    @RequiresPermissions("event:task:remove")
    @PostMapping("/attachment/remove")
    @ResponseBody
    public AjaxResult remove(Task task)
    {
        task.setCreateBy(ShiroUtils.getLoginName());
        task.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        task.setCompanyId(ShiroUtils.getSysUser().getCompany().getDeptId());
        return toAjax(taskService.updateAttachment(task));
    }

}
