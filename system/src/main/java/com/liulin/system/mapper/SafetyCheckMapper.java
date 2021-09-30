package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.SafetyCheck;

/**
 * Safety CheckMapper接口
 * 
 * @author liulin
 * @date 2021-09-29
 */
public interface SafetyCheckMapper 
{
    /**
     * 查询Safety Check
     * 
     * @param safetyCheckId Safety CheckID
     * @return Safety Check
     */
    public SafetyCheck selectSafetyCheckById(Long safetyCheckId);

    /**
     * 查询Safety Check列表
     * 
     * @param safetyCheck Safety Check
     * @return Safety Check集合
     */
    public List<SafetyCheck> selectSafetyCheckList(SafetyCheck safetyCheck);

    /**
     * 新增Safety Check
     * 
     * @param safetyCheck Safety Check
     * @return 结果
     */
    public int insertSafetyCheck(SafetyCheck safetyCheck);

    /**
     * 修改Safety Check
     * 
     * @param safetyCheck Safety Check
     * @return 结果
     */
    public int updateSafetyCheck(SafetyCheck safetyCheck);

    /**
     * 删除Safety Check
     * 
     * @param safetyCheckId Safety CheckID
     * @return 结果
     */
    public int deleteSafetyCheckById(Long safetyCheckId);

    /**
     * 批量删除Safety Check
     * 
     * @param safetyCheckIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSafetyCheckByIds(String[] safetyCheckIds);
}
