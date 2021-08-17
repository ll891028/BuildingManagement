package com.liulin.system.service.impl;

import java.util.Arrays;
import java.util.List;

import com.liulin.common.constant.Constants;
import com.liulin.common.constant.UserConstants;
import com.liulin.common.core.domain.entity.SysUser;
import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.common.utils.security.Md5Utils;
import com.liulin.system.domain.BuildingLevel;
import com.liulin.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.ResidentMapper;
import com.liulin.system.domain.Resident;
import com.liulin.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * residentService业务层处理
 * 
 * @author liulin
 * @date 2021-08-06
 */
@Service
public class ResidentServiceImpl implements IResidentService 
{
    @Autowired
    private ResidentMapper residentMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private IBuildingLevelService buildingLevelService;

    @Autowired
    private ICarPlateService carPlateService;

    @Autowired
    private ICarSpaceService carSpaceService;


    /**
     * 查询resident
     * 
     * @param residentId residentID
     * @return resident
     */
    @Override
    public Resident selectResidentById(Long residentId)
    {
        return residentMapper.selectResidentById(residentId);
    }

    /**
     * 查询resident列表
     * 
     * @param resident resident
     * @return resident
     */
    @Override
    public List<Resident> selectResidentList(Resident resident)
    {
        return residentMapper.selectResidentList(resident);
    }

    /**
     * 新增resident
     * 
     * @param resident resident
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertResident(Resident resident)
    {
        //通过email查询用户是否存在
        SysUser query = new SysUser();
        query.setEmail(resident.getEmail());
        String result = sysUserService.checkEmailUnique(query);
        if(UserConstants.USER_EMAIL_UNIQUE.equals(result)){
            BuildingLevel buildingLevel = buildingLevelService.selectBuildingLevelById(resident.getLevelId());

            //生成user数据
            SysUser saver = new SysUser();
            saver.setEmail(resident.getEmail());
            //登录账号为邮箱地址
            saver.setLoginName(resident.getEmail());
            saver.setUserName(resident.getFirstName()+" "+resident.getLastName());
            String password = configService.selectConfigByKey("sys.user.initPassword");
            saver.setPassword(Md5Utils.hash(saver.getLoginName() + password));
            saver.setCreateBy(resident.getCreateBy());
            //resident角色
            saver.setRoleIds(new Long[]{Constants.RESIDENT});
            saver.setDeptId(buildingLevel.getBuildingId());
            sysUserService.insertUser(saver);
            resident.setUserId(saver.getUserId());
        }else{
            SysUser sysUser = sysUserService.selectUserByEmail(resident.getEmail());
            resident.setUserId(sysUser.getUserId());
        }
        resident.setCreateTime(DateUtils.getNowDate());
        return residentMapper.insertResident(resident);
    }

    /**
     * 修改resident
     * 
     * @param resident resident
     * @return 结果
     */
    @Override
    public int updateResident(Resident resident)
    {
        resident.setUpdateTime(DateUtils.getNowDate());
        return residentMapper.updateResident(resident);
    }

    /**
     * 删除resident对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteResidentByIds(String ids)
    {
        return residentMapper.deleteResidentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除resident信息
     * 
     * @param residentId residentID
     * @return 结果
     */
    @Override
    public int deleteResidentById(Long residentId)
    {
        return residentMapper.deleteResidentById(residentId);
    }

    @Override
    public String checkUnitNumberUnique(Resident resident) {
        if(StringUtils.isNotNull(residentMapper.checkUnitNumberUnique(resident.getBuildingId(),resident.getUnitNumber(),
                resident.getResidentId()))){
            return UserConstants.NAME_UNIQUE;
        }
        return UserConstants.NAME_NOT_UNIQUE;
    }
}
