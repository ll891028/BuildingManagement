package com.liulin.system.service.impl;

import com.liulin.common.annotation.DataScope;
import com.liulin.common.config.ServerConfig;
import com.liulin.common.constant.UserConstants;
import com.liulin.common.core.domain.Ztree;
import com.liulin.common.core.domain.entity.SysDept;
import com.liulin.common.core.domain.entity.SysRole;
import com.liulin.common.core.text.Convert;
import com.liulin.common.exception.BusinessException;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.system.mapper.SysDeptMapper;
import com.liulin.system.service.IAttachmentService;
import com.liulin.system.service.IBuildingLevelService;
import com.liulin.system.service.IServizeService;
import com.liulin.system.service.ISysDeptService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 部门管理 服务实现
 * 
 * @author liulin
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService
{
    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private IBuildingLevelService buildingLevelService;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private IServizeService servizeService;

    /**
     * 查询部门管理数据
     * 
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept)
    {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 查询部门管理树
     * 
     * @param dept 部门信息
     * @return 所有部门信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<Ztree> selectDeptTree(SysDept dept)
    {
        List<SysDept> deptList = deptMapper.selectDeptList(dept);
        List<Ztree> ztrees = initZtree(deptList);
        return ztrees;
    }

    /**
     * 查询部门管理树（排除下级）
     * 
     * @param dept 部门ID
     * @return 所有部门信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<Ztree> selectDeptTreeExcludeChild(SysDept dept)
    {
        Long deptId = dept.getDeptId();
        List<SysDept> deptList = deptMapper.selectDeptList(dept);
        Iterator<SysDept> it = deptList.iterator();
        while (it.hasNext())
        {
            SysDept d = (SysDept) it.next();
            if (d.getDeptId().intValue() == deptId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""))
            {
                it.remove();
            }
        }
        List<Ztree> ztrees = initZtree(deptList);
        return ztrees;
    }

    /**
     * 根据角色ID查询部门（数据权限）
     *
     * @param role 角色对象
     * @return 部门列表（数据权限）
     */
    @Override
    public List<Ztree> roleDeptTreeData(SysRole role)
    {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<SysDept> deptList = selectDeptList(new SysDept());
        if (StringUtils.isNotNull(roleId))
        {
            List<String> roleDeptList = deptMapper.selectRoleDeptTree(roleId);
            ztrees = initZtree(deptList, roleDeptList);
        }
        else
        {
            ztrees = initZtree(deptList);
        }
        return ztrees;
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysDept> deptList)
    {
        return initZtree(deptList, null);
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysDept> deptList, List<String> roleDeptList)
    {

        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (SysDept dept : deptList)
        {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
            {
                Ztree ztree = new Ztree();
                ztree.setId(dept.getDeptId());
                ztree.setpId(dept.getParentId());
                ztree.setName(dept.getDeptName());
                ztree.setTitle(dept.getDeptName());
                if (isCheck)
                {
                    ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    /**
     * 查询部门人数
     * 
     * @param parentId 部门ID
     * @return 结果
     */
    @Override
    public int selectDeptCount(Long parentId)
    {
        SysDept dept = new SysDept();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId)
    {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId)
    {
        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * 新增保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertDept(SysDept dept)
    {
        SysDept info = deptMapper.selectDeptById(dept.getParentId());
        // 如果父节点不为"正常"状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new BusinessException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        String[] attachmentUrls = dept.getAttachmentUrls().split(",");
        String[] originalFileNames = dept.getOriginalFileNames().split(",");
        String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                dept.getCreateBy(),dept.getDeptId(),info.getDeptId() );
        dept.setAttachmentIds(attachmentIds);
        int result = deptMapper.insertDept(dept);
        if(dept.getIfGroundFloor()!=null && dept.getLevels()!=null && dept.getBasements()!=null){
            buildingLevelService.generateDefaultLevel(dept.getDeptId(),dept.getLevels().intValue(),
                    dept.getIfGroundFloor().intValue(),dept.getBasements(), ShiroUtils.getLoginName(),null);
        }
        if(dept.getType().equals(SysDept.COMPANY)){
            //部门为Company
            servizeService.generateDefaultService(dept.getDeptId(),dept.getCreateBy());
        }
        return result;
    }

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDept(SysDept dept)
    {
        SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        SysDept oldDept = selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }

        //更新附件表
        if(StringUtils.isNotEmpty(dept.getAttachmentUrls())){
            String[] attachmentUrls = dept.getAttachmentUrls().split(",");
            String[] originalFileNames = dept.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                    dept.getUpdateBy(), dept.getDeptId(),newParentDept.getDeptId() );
            dept.setAttachmentIds(attachmentIds);
        }else{
            dept.setAttachmentIds("");
        }

        int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())&&newParentDept!=null)
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatusNormal(dept);
        }

        return result;
    }

    @Override
    public int updateDeptAttachment(SysDept dept) {
        Integer attachmentId = dept.getAttachmentId();
        SysDept sysDept = selectDeptById(dept.getDeptId());
//        String attachmentIds = sysDept.getAttachmentIds();
//        String[] attachmentIdArray = attachmentIds.split(",");
//        String newAttachmentIds="";
//        for (String s : attachmentIdArray) {
//            if(!s.equals(String.valueOf(attachmentId))){
//                newAttachmentIds += s+",";
//            }
//        }
        if(StringUtils.isNotEmpty(sysDept.getAttachmentUrls())){
            String[] attachmentUrls = sysDept.getAttachmentUrls().split(",");
            String[] originalFileNames = sysDept.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                    sysDept.getCreateBy(),sysDept.getDeptId(), sysDept.getParentId());
            sysDept.setAttachmentIds(attachmentIds);
        }
//        sysDept.setAttachmentIds(newAttachmentIds);
        return deptMapper.updateDept(sysDept);
    }

    /**
     * 修改该部门的父级部门状态
     * 
     * @param dept 当前部门
     */
    private void updateParentDeptStatusNormal(SysDept dept)
    {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        deptMapper.updateDeptStatusNormal(deptIds);
    }

    /**
     * 修改子元素关系
     * 
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children)
        {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 根据部门ID查询信息
     * 
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Long deptId)
    {
        return deptMapper.selectDeptById(deptId);
    }

    @Override
    public List<SysDept> selectDeptByIds(String[] deptIds) {
        return deptMapper.selectDeptByIds(deptIds);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     * 
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(Long deptId)
    {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    @Override
    public List<SysDept> selectChildrenDeptByIdAndType(SysDept sysDept) {
        return deptMapper.selectChildrenDeptByIdAndType(sysDept);
    }

    /**
     * 校验部门名称是否唯一
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDept dept)
    {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }

    @Override
    public int generateLevel(SysDept dept) {

        buildingLevelService.generateDefaultLevel(dept.getDeptId(),dept.getLevels(),dept.getIfGroundFloor(),
                dept.getBasements(),dept.getCreateBy(),dept.getAreaName());
        return deptMapper.updateDept(dept);
    }

    @Override
    public SysDept selectCompanyByBuildingId(Long buildingId) {
        SysDept sysDept = new SysDept();
        sysDept.setParentId(buildingId);
        while (!sysDept.getParentId().equals(100L)){
            sysDept = deptMapper.selectParentDept(sysDept.getParentId());
        }
        return sysDept;
    }

}
