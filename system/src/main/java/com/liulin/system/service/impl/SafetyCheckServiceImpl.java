package com.liulin.system.service.impl;

import com.liulin.common.core.text.Convert;
import com.liulin.common.utils.DateUtils;
import com.liulin.system.domain.SafetyCheck;
import com.liulin.system.domain.SafetyCheckAsset;
import com.liulin.system.domain.Schedule;
import com.liulin.system.mapper.SafetyCheckMapper;
import com.liulin.system.service.ISafetyCheckAssetService;
import com.liulin.system.service.ISafetyCheckService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Safety CheckService业务层处理
 * 
 * @author liulin
 * @date 2021-09-29
 */
@Service
public class SafetyCheckServiceImpl implements ISafetyCheckService 
{
    @Autowired
    private SafetyCheckMapper safetyCheckMapper;

    @Autowired
    private ISafetyCheckAssetService safetyCheckAssetService;

    /**
     * 查询Safety Check
     * 
     * @param safetyCheckId Safety CheckID
     * @return Safety Check
     */
    @Override
    public SafetyCheck selectSafetyCheckById(Long safetyCheckId)
    {
        return safetyCheckMapper.selectSafetyCheckById(safetyCheckId);
    }

    /**
     * 查询Safety Check列表
     * 
     * @param safetyCheck Safety Check
     * @return Safety Check
     */
    @Override
    public List<SafetyCheck> selectSafetyCheckList(SafetyCheck safetyCheck)
    {
        return safetyCheckMapper.selectSafetyCheckList(safetyCheck);
    }

    /**
     * 新增Safety Check
     * 
     * @param safetyCheck Safety Check
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSafetyCheck(SafetyCheck safetyCheck)
    {
        safetyCheck.setCreateTime(DateUtils.getNowDate());
        int result = safetyCheckMapper.insertSafetyCheck(safetyCheck);
        SafetyCheck updateNo = new SafetyCheck();
        updateNo.setSafetyCheckId(safetyCheck.getSafetyCheckId());
        updateNo.setSafetyCheckNo("SC-"+safetyCheck.getSafetyCheckId());
        safetyCheckMapper.updateSafetyCheck(updateNo);
        if(CollectionUtils.isNotEmpty(safetyCheck.getAssetIds())){
            for (Long assetId : safetyCheck.getAssetIds()) {
                SafetyCheckAsset safetyCheckAsset = new SafetyCheckAsset();
                safetyCheckAsset.setSafetyCheckId(safetyCheck.getSafetyCheckId());
                safetyCheckAsset.setAssetId(assetId);
                safetyCheckAssetService.insertSafetyCheckAsset(safetyCheckAsset);
            }
        }

        return result;
    }

    /**
     * 修改Safety Check
     * 
     * @param safetyCheck Safety Check
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSafetyCheck(SafetyCheck safetyCheck)
    {
        safetyCheck.setUpdateTime(DateUtils.getNowDate());
        safetyCheckAssetService.deleteSafetyCheckAssetByCheckId(safetyCheck.getSafetyCheckId());
        if(CollectionUtils.isNotEmpty(safetyCheck.getAssetIds())){
            for (Long assetId : safetyCheck.getAssetIds()) {
                SafetyCheckAsset safetyCheckAsset = new SafetyCheckAsset();
                safetyCheckAsset.setSafetyCheckId(safetyCheck.getSafetyCheckId());
                safetyCheckAsset.setAssetId(assetId);
                safetyCheckAssetService.insertSafetyCheckAsset(safetyCheckAsset);
            }
        }

        if(safetyCheck.getDateComplete()!=null && safetyCheck.getStatus() == Schedule.CLOSED){
            //复制一条数据 计算下个周期数据
            SafetyCheck saver = new SafetyCheck();
            BeanUtils.copyProperties(safetyCheck,saver);
            saver.setSafetyCheckId(null);
            saver.setDateComplete(null);
            saver.setStatus(Schedule.PENDING);
            saver.setCreateBy(safetyCheck.getUpdateBy());
            saver.setCreateTime(safetyCheck.getUpdateTime());
            saver.setDateSchedule(DateUtils.addTime(safetyCheck.getDateComplete(),safetyCheck.getFrequency()));
            safetyCheckMapper.insertSafetyCheck(saver);
            if(CollectionUtils.isNotEmpty(safetyCheck.getAssetIds())){
                for (Long assetId : safetyCheck.getAssetIds()) {
                    SafetyCheckAsset safetyCheckAsset = new SafetyCheckAsset();
                    safetyCheckAsset.setSafetyCheckId(saver.getSafetyCheckId());
                    safetyCheckAsset.setAssetId(assetId);
                    safetyCheckAssetService.insertSafetyCheckAsset(safetyCheckAsset);
                }
            }
        }

        return safetyCheckMapper.updateSafetyCheck(safetyCheck);
    }

    /**
     * 删除Safety Check对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSafetyCheckByIds(String ids)
    {
        return safetyCheckMapper.deleteSafetyCheckByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除Safety Check信息
     * 
     * @param safetyCheckId Safety CheckID
     * @return 结果
     */
    @Override
    public int deleteSafetyCheckById(Long safetyCheckId)
    {
        return safetyCheckMapper.deleteSafetyCheckById(safetyCheckId);
    }

}
