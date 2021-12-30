package com.liulin.web.controller.system;

import com.liulin.common.annotation.Log;
import com.liulin.common.constant.UserConstants;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.core.domain.Ztree;
import com.liulin.common.core.domain.entity.SysDept;
import com.liulin.common.core.domain.entity.SysRole;
import com.liulin.common.core.domain.entity.SysUser;
import com.liulin.common.core.redis.RedisCache;
import com.liulin.common.enums.BusinessType;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.system.domain.Attachment;
import com.liulin.system.service.IAttachmentService;
import com.liulin.system.service.ISysDeptService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门信息
 * 
 * @author liulin
 */
@Controller
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController
{
    private String prefix = "system/dept";

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private RedisCache redisCache;

    @RequiresPermissions("system:dept:view")
    @GetMapping()
    public String dept()
    {
        return prefix + "/dept";
    }

    @RequiresPermissions("system:dept:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysDept> list(SysDept dept)
    {
        List<SysDept> deptList = deptService.selectDeptList(dept);
        return deptList;
    }

    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap)
    {
//        if (!ShiroUtils.getSysUser().isAdmin())
//        {
//            parentId = ShiroUtils.getSysUser().getDeptId();
//        }
        mmap.put("dept", deptService.selectDeptById(parentId));
        return prefix + "/add";
    }

    /**
     * 新增保存部门
     */
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dept:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysDept dept)
    {
//        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
//        {
//            return error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
//        }
        dept.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap)
    {
        SysDept dept = deptService.selectDeptById(deptId);
        String attachmentIds = dept.getAttachmentIds();
        if(StringUtils.isNotEmpty(attachmentIds)){
            String[] attachmentIdStrArray = attachmentIds.split(",");
           long [] attachmentIdArray =
                    Arrays.asList(attachmentIdStrArray).stream().mapToLong(Long::parseLong).toArray();
            List<Attachment> attachments = attachmentService.selectAttachmentByIds(attachmentIdArray);
            if(CollectionUtils.isNotEmpty(attachments)){
                mmap.put("attachments",attachments);
            }
        }

        if (StringUtils.isNotNull(dept) && 100L == deptId)
        {
            dept.setParentName("无");
        }
        mmap.put("dept", dept);
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dept:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysDept dept)
    {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            return error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        else if (dept.getParentId().equals(dept.getDeptId()))
        {
            return error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
                && deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0)
        {
            return AjaxResult.error("该部门包含未停用的子部门！");
        }
        dept.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除
     */
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dept:remove")
    @GetMapping("/remove/{deptId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("deptId") Long deptId)
    {
        if (deptService.selectDeptCount(deptId) > 0)
        {
            return AjaxResult.warn("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId))
        {
            return AjaxResult.warn("部门存在用户,不允许删除");
        }
        return toAjax(deptService.deleteDeptById(deptId));
    }

    /**
     * 删除附件
     */
    @Log(title = "删除building附件", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dept:edit")
    @PostMapping("/attachment/remove")
    @ResponseBody
    public AjaxResult remove(SysDept dept)
    {

        return  AjaxResult.success();
    }

    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(SysDept dept)
    {
        return deptService.checkDeptNameUnique(dept);
    }

    /**
     * 选择多选部门树
     *
     * @param deptIds 部门ID
     */
    @GetMapping(value = { "/selectMultiDeptTree/{deptIds}"})
    public String selectMultiDeptTree(@PathVariable("deptIds") String deptIds, ModelMap mmap)
    {
        String[] deptIdArray = deptIds.split(",");
        List<SysDept> sysDepts = deptService.selectDeptByIds(deptIdArray);
        mmap.put("depts",sysDepts);
        return prefix + "/multiTree";
    }
    /**
     * 选择部门树
     * 
     * @param deptId 部门ID
     * @param excludeId 排除ID
     */
    @GetMapping(value = { "/selectDeptTree/{deptId}", "/selectDeptTree/{deptId}/{excludeId}" })
    public String selectDeptTree(@PathVariable("deptId") Long deptId,
            @PathVariable(value = "excludeId", required = false) String excludeId, ModelMap mmap)
    {
        mmap.put("dept", deptService.selectDeptById(deptId));
        mmap.put("excludeId", excludeId);
        return prefix + "/tree";
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = deptService.selectDeptTree(new SysDept());
        return ztrees;
    }

    /**
     * 加载部门列表树（排除下级）
     */
    @GetMapping("/treeData/{excludeId}")
    @ResponseBody
    public List<Ztree> treeDataExcludeChild(@PathVariable(value = "excludeId", required = false) Long excludeId)
    {
        SysDept dept = new SysDept();
        dept.setDeptId(excludeId);
        List<Ztree> ztrees = deptService.selectDeptTreeExcludeChild(dept);
        return ztrees;
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/roleDeptTreeData")
    @ResponseBody
    public List<Ztree> deptTreeData(SysRole role)
    {
        List<Ztree> ztrees = deptService.roleDeptTreeData(role);
        return ztrees;
    }

    @GetMapping("/changeUserBuilding/{buildingId}")
    @ResponseBody
    public AjaxResult changeUserBuilding(@PathVariable Long buildingId){
        SysDept sysDept = deptService.selectDeptById(buildingId);
        SysUser sysUser = ShiroUtils.getSysUser();
        sysUser.setBuilding(sysDept);
        SysDept company = deptService.selectCompanyByBuildingId(sysDept.getParentId());
        sysUser.setCompany(company);
        ShiroUtils.setSysUser(sysUser);
        redisCache.setCacheObject("user:building:"+sysUser.getUserId(),sysUser.getBuilding());
//        CacheUtils.put("user:building:"+sysUser.getUserId(),sysUser.getBuilding());
        Map data = new HashMap();
        data.put("company",company);
        return AjaxResult.success("success",data);
    }


}
