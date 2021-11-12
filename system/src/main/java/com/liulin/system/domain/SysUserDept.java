package com.liulin.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * UserDept对象 sys_user_dept
 * 
 * @author liulin
 * @date 2021-11-03
 */
public class SysUserDept extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** User Dept Id */
    private Long userDeptId;

    /** User Id */
    @Excel(name = "User Id")
    private Long userId;

    /** Dept Id */
    @Excel(name = "Dept Id")
    private Long deptId;

    private String deptName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setUserDeptId(Long userDeptId)
    {
        this.userDeptId = userDeptId;
    }

    public Long getUserDeptId() 
    {
        return userDeptId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userDeptId", getUserDeptId())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .toString();
    }
}
