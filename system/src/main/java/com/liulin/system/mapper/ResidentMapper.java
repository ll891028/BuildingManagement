package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.Resident;

/**
 * residentMapper接口
 * 
 * @author liulin
 * @date 2021-08-05
 */
public interface ResidentMapper 
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
     * 删除resident
     * 
     * @param residentId residentID
     * @return 结果
     */
    public int deleteResidentById(Long residentId);

    /**
     * 批量删除resident
     * 
     * @param residentIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteResidentByIds(String[] residentIds);
}
