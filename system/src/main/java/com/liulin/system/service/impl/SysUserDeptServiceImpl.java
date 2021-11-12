package com.liulin.system.service.impl;

import com.liulin.common.core.text.Convert;
import com.liulin.system.domain.SysUserDept;
import com.liulin.system.mapper.SysUserDeptMapper;
import com.liulin.system.service.ISysUserDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserDeptService业务层处理
 * 
 * @author liulin
 * @date 2021-11-03
 */
@Service
public class SysUserDeptServiceImpl implements ISysUserDeptService 
{
    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;

    /**
     * 查询UserDept
     * 
     * @param userDeptId UserDeptID
     * @return UserDept
     */
    @Override
    public SysUserDept selectSysUserDeptById(Long userDeptId)
    {
        return sysUserDeptMapper.selectSysUserDeptById(userDeptId);
    }

    @Override
    public List<SysUserDept> selectSysUserDeptByUserId(Long userId) {
        SysUserDept query = new SysUserDept();
        query.setUserId(userId);
        return sysUserDeptMapper.selectSysUserDeptList(query);
    }

    /**
     * 查询UserDept列表
     * 
     * @param sysUserDept UserDept
     * @return UserDept
     */
    @Override
    public List<SysUserDept> selectSysUserDeptList(SysUserDept sysUserDept)
    {
        return sysUserDeptMapper.selectSysUserDeptList(sysUserDept);
    }

    /**
     * 新增UserDept
     * 
     * @param sysUserDept UserDept
     * @return 结果
     */
    @Override
    public int insertSysUserDept(SysUserDept sysUserDept)
    {
        return sysUserDeptMapper.insertSysUserDept(sysUserDept);
    }

    /**
     * 修改UserDept
     * 
     * @param sysUserDept UserDept
     * @return 结果
     */
    @Override
    public int updateSysUserDept(SysUserDept sysUserDept)
    {
        return sysUserDeptMapper.updateSysUserDept(sysUserDept);
    }

    /**
     * 删除UserDept对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysUserDeptByIds(String ids)
    {
        return sysUserDeptMapper.deleteSysUserDeptByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除UserDept信息
     * 
     * @param userDeptId UserDeptID
     * @return 结果
     */
    @Override
    public int deleteSysUserDeptById(Long userDeptId)
    {
        return sysUserDeptMapper.deleteSysUserDeptById(userDeptId);
    }
}
