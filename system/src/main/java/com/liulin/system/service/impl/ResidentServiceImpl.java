package com.liulin.system.service.impl;

import com.liulin.common.constant.Constants;
import com.liulin.common.constant.UserConstants;
import com.liulin.common.core.domain.entity.SysUser;
import com.liulin.common.core.text.Convert;
import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.common.utils.security.Md5Utils;
import com.liulin.system.domain.BuildingLevel;
import com.liulin.system.domain.CarPlate;
import com.liulin.system.domain.CarSpace;
import com.liulin.system.domain.Resident;
import com.liulin.system.mapper.ResidentMapper;
import com.liulin.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private IAttachmentService attachmentService;



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
        BuildingLevel buildingLevel = buildingLevelService.selectBuildingLevelById(resident.getLevelId());
        if(UserConstants.USER_EMAIL_UNIQUE.equals(result)){


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

        if(StringUtils.isNotEmpty(resident.getAttachmentUrls())){
            String[] attachmentUrls = resident.getAttachmentUrls().split(",");
            String[] originalFileNames = resident.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,resident.getCreateBy(),resident.getBuildingId() ,resident.getCompanyId() );
            resident.setAttachmentIds(attachmentIds);
        }

        int i = residentMapper.insertResident(resident);
        if(StringUtils.isNotEmpty(resident.getCarSpaceNumber1())){
            CarSpace carSpace = new CarSpace();
            carSpace.setBuildingId(buildingLevel.getBuildingId());
            carSpace.setResidentId(resident.getResidentId());
            carSpace.setCarSpaceNumber(resident.getCarSpaceNumber1());
            carSpace.setCreateBy(resident.getCreateBy());
            if(StringUtils.isNotEmpty(resident.getCarPlateNumber1())){
                CarPlate carPlate = new CarPlate();
                carPlate.setBuildingId(buildingLevel.getBuildingId());
                carPlate.setCarPlateNumber(resident.getCarPlateNumber1());
                carPlate.setResidentId(resident.getResidentId());
                carPlate.setCreateBy(resident.getCreateBy());
                carPlateService.insertCarPlate(carPlate);
                carSpace.setCarPlateId(carPlate.getCarPlateId());
            }
            carSpace.setOrder(1);
            carSpaceService.insertCarSpace(carSpace);
        }
        if(StringUtils.isNotEmpty(resident.getCarSpaceNumber2())){
            CarSpace carSpace = new CarSpace();
            carSpace.setBuildingId(buildingLevel.getBuildingId());
            carSpace.setResidentId(resident.getResidentId());
            carSpace.setCarSpaceNumber(resident.getCarSpaceNumber2());
            carSpace.setCreateBy(resident.getCreateBy());
            if(StringUtils.isNotEmpty(resident.getCarPlateNumber2())){
                CarPlate carPlate = new CarPlate();
                carPlate.setBuildingId(buildingLevel.getBuildingId());
                carPlate.setCarPlateNumber(resident.getCarPlateNumber2());
                carPlate.setResidentId(resident.getResidentId());
                carPlate.setCreateBy(resident.getCreateBy());
                carPlateService.insertCarPlate(carPlate);
                carSpace.setCarPlateId(carPlate.getCarPlateId());
            }
            carSpace.setOrder(2);
            carSpaceService.insertCarSpace(carSpace);
        }
        if(StringUtils.isNotEmpty(resident.getCarSpaceNumber3())){
            CarSpace carSpace = new CarSpace();
            carSpace.setBuildingId(buildingLevel.getBuildingId());
            carSpace.setResidentId(resident.getResidentId());
            carSpace.setCarSpaceNumber(resident.getCarSpaceNumber3());
            carSpace.setCreateBy(resident.getCreateBy());
            if(StringUtils.isNotEmpty(resident.getCarPlateNumber3())){
                CarPlate carPlate = new CarPlate();
                carPlate.setBuildingId(buildingLevel.getBuildingId());
                carPlate.setCarPlateNumber(resident.getCarPlateNumber3());
                carPlate.setResidentId(resident.getResidentId());
                carPlate.setCreateBy(resident.getCreateBy());
                carPlateService.insertCarPlate(carPlate);
                carSpace.setCarPlateId(carPlate.getCarPlateId());
            }
            carSpace.setOrder(3);
            carSpaceService.insertCarSpace(carSpace);
        }
        return i;
    }



    /**
     * 修改resident
     * 
     * @param resident resident
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateResident(Resident resident)
    {
        resident.setUpdateTime(DateUtils.getNowDate());
        BuildingLevel buildingLevel = buildingLevelService.selectBuildingLevelById(resident.getLevelId());
        //删除关联carspace carplate数据
        carSpaceService.deleteCarSpaceByResidentId(resident.getResidentId());
        carPlateService.deleteCarPlateByResidentId(resident.getResidentId());
        if(resident.getCarparkSpaceAmount()>=1){
            if(StringUtils.isNotEmpty(resident.getCarSpaceNumber1())){
                CarSpace carSpace = new CarSpace();
                carSpace.setBuildingId(buildingLevel.getBuildingId());
                carSpace.setResidentId(resident.getResidentId());
                carSpace.setCarSpaceNumber(resident.getCarSpaceNumber1());
                carSpace.setCreateBy(resident.getCreateBy());
                if(StringUtils.isNotEmpty(resident.getCarPlateNumber1())){
                    CarPlate carPlate = new CarPlate();
                    carPlate.setBuildingId(buildingLevel.getBuildingId());
                    carPlate.setCarPlateNumber(resident.getCarPlateNumber1());
                    carPlate.setResidentId(resident.getResidentId());
                    carPlate.setCreateBy(resident.getCreateBy());
                    carPlateService.insertCarPlate(carPlate);
                    carSpace.setCarPlateId(carPlate.getCarPlateId());
                }
                carSpace.setOrder(1);
                carSpaceService.insertCarSpace(carSpace);
            }
        }
        if(resident.getCarparkSpaceAmount()>=2) {
            if (StringUtils.isNotEmpty(resident.getCarSpaceNumber2())) {
                CarSpace carSpace = new CarSpace();
                carSpace.setBuildingId(buildingLevel.getBuildingId());
                carSpace.setResidentId(resident.getResidentId());
                carSpace.setCarSpaceNumber(resident.getCarSpaceNumber2());
                carSpace.setCreateBy(resident.getCreateBy());
                if (StringUtils.isNotEmpty(resident.getCarPlateNumber2())) {
                    CarPlate carPlate = new CarPlate();
                    carPlate.setBuildingId(buildingLevel.getBuildingId());
                    carPlate.setCarPlateNumber(resident.getCarPlateNumber2());
                    carPlate.setResidentId(resident.getResidentId());
                    carPlate.setCreateBy(resident.getCreateBy());
                    carPlateService.insertCarPlate(carPlate);
                    carSpace.setCarPlateId(carPlate.getCarPlateId());
                }
                carSpace.setOrder(2);
                carSpaceService.insertCarSpace(carSpace);
            }
        }
        if(resident.getCarparkSpaceAmount()>=3) {
            if (StringUtils.isNotEmpty(resident.getCarSpaceNumber3())) {
                CarSpace carSpace = new CarSpace();
                carSpace.setBuildingId(buildingLevel.getBuildingId());
                carSpace.setResidentId(resident.getResidentId());
                carSpace.setCarSpaceNumber(resident.getCarSpaceNumber3());
                carSpace.setCreateBy(resident.getCreateBy());
                if (StringUtils.isNotEmpty(resident.getCarPlateNumber3())) {
                    CarPlate carPlate = new CarPlate();
                    carPlate.setBuildingId(buildingLevel.getBuildingId());
                    carPlate.setCarPlateNumber(resident.getCarPlateNumber3());
                    carPlate.setResidentId(resident.getResidentId());
                    carPlate.setCreateBy(resident.getCreateBy());
                    carPlateService.insertCarPlate(carPlate);
                    carSpace.setCarPlateId(carPlate.getCarPlateId());
                }
                carSpace.setOrder(3);
                carSpaceService.insertCarSpace(carSpace);
            }
        }

        if(StringUtils.isNotEmpty(resident.getAttachmentUrls())){
            String[] attachmentUrls = resident.getAttachmentUrls().split(",");
            String[] originalFileNames = resident.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                    resident.getUpdateBy(),resident.getBuildingId() ,resident.getCompanyId());
            resident.setAttachmentIds(attachmentIds);
        }else {
            resident.setAttachmentIds("");
        }

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

    @Override
    public int updateAttachment(Resident resident) {
        if(StringUtils.isNotEmpty(resident.getAttachmentUrls())){
            String[] attachmentUrls = resident.getAttachmentUrls().split(",");
            String[] originalFileNames = resident.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                    resident.getCreateBy(),resident.getBuildingId() ,resident.getCompanyId() );
            resident.setAttachmentIds(attachmentIds);
        }
        return residentMapper.updateResident(resident);
    }

    @Override
    public List<Resident> selectResidentByIds(List<Long> residentIdList) {
        return residentMapper.selectResidentByIds(residentIdList);
    }
}
