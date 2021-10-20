package com.liulin.system.service;

import java.util.List;
import com.liulin.system.domain.Defects;

/**
 * Defects RegisterService接口
 * 
 * @author liulin
 * @date 2021-10-15
 */
public interface IDefectsService 
{
    /**
     * 查询Defects Register
     * 
     * @param defectId Defects RegisterID
     * @return Defects Register
     */
    public Defects selectDefectsById(Long defectId);

    /**
     * 查询Defects Register列表
     * 
     * @param defects Defects Register
     * @return Defects Register集合
     */
    public List<Defects> selectDefectsList(Defects defects);

    /**
     * 新增Defects Register
     * 
     * @param defects Defects Register
     * @return 结果
     */
    public int insertDefects(Defects defects);

    /**
     * 修改Defects Register
     * 
     * @param defects Defects Register
     * @return 结果
     */
    public int updateDefects(Defects defects);

    /**
     * 批量删除Defects Register
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDefectsByIds(String ids);

    /**
     * 删除Defects Register信息
     * 
     * @param defectId Defects RegisterID
     * @return 结果
     */
    public int deleteDefectsById(Long defectId);

    public int updateDefectsAttachment(Defects defects);
}
