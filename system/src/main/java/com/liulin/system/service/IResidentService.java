package com.liulin.system.service;

import com.liulin.system.domain.Resident;

import java.util.List;

/**
 * residentService接口
 * 
 * @author liulin
 * @date 2021-08-06
 */
public interface IResidentService 
{
    /**
     * 查询resident
     * 
     * @param residentId residentID
     * @return resident
     */
    public Resident selectResidentById(Long residentId);

    /**
     * 查询resident列表
     * 
     * @param resident resident
     * @return resident集合
     */
    public List<Resident> selectResidentList(Resident resident);

    /**
     * 新增resident
     * 
     * @param resident resident
     * @return 结果
     */
    public int insertResident(Resident resident);

    /**
     * 修改resident
     * 
     * @param resident resident
     * @return 结果
     */
    public int updateResident(Resident resident);

    /**
     * 批量删除resident
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteResidentByIds(String ids);

    /**
     * 删除resident信息
     * 
     * @param residentId residentID
     * @return 结果
     */
    public int deleteResidentById(Long residentId);

    String checkUnitNumberUnique(Resident resident);

    public int updateAttachment(Resident resident);

    List<Resident> selectResidentByIds(List<Long> residentIdList);
}
