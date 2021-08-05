package com.liulin.system.service.impl;

import java.util.List;
import com.liulin.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.ResidentMapper;
import com.liulin.system.domain.Resident;
import com.liulin.system.service.IResidentService;
import com.liulin.common.core.text.Convert;

/**
 * residentService业务层处理
 * 
 * @author liulin
 * @date 2021-08-05
 */
@Service
public class ResidentServiceImpl implements IResidentService 
{
    @Autowired
    private ResidentMapper residentMapper;

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
    public int insertResident(Resident resident)
    {
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
}
