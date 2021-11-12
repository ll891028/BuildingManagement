package com.liulin.system.mapper;

import com.liulin.system.domain.SysUserDept;

import java.util.List;

/**
 * UserDeptMapper接口
 * 
 * @author liulin
 * @date 2021-11-03
 */
public interface SysUserDeptMapper 
{
    /**
     * 查询UserDept
     * 
     * @param userDeptId UserDeptID
     * @return UserDept
     */
    public SysUserDept selectSysUserDeptById(Long userDeptId);

    /**
     * 查询UserDept列表
     * 
     * @param sysUserDept UserDept
     * @return UserDept集合
     */
    public List<SysUserDept> selectSysUserDeptList(SysUserDept sysUserDept);

    /**
     * 新增UserDept
     * 
     * @param sysUserDept UserDept
     * @return 结果
     */
    public int insertSysUserDept(SysUserDept sysUserDept);

    /**
     * 修改UserDept
     * 
     * @param sysUserDept UserDept
     * @return 结果
     */
    public int updateSysUserDept(SysUserDept sysUserDept);

    /**
     * 删除UserDept
     * 
     * @param userDeptId UserDeptID
     * @return 结果
     */
    public int deleteSysUserDeptById(Long userDeptId);

    public int deleteSysUserDeptByUserId(Long userId);


    /**
     * 批量删除UserDept
     * 
     * @param userDeptIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUserDeptByIds(String[] userDeptIds);
}
