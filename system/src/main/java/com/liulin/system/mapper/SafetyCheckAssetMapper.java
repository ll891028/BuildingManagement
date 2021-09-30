package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.SafetyCheckAsset;

/**
 * Safety Check AssetMapper接口
 * 
 * @author liulin
 * @date 2021-09-29
 */
public interface SafetyCheckAssetMapper 
{
    /**
     * 查询Safety Check Asset
     * 
     * @param safetyCheckAssetId Safety Check AssetID
     * @return Safety Check Asset
     */
    public SafetyCheckAsset selectSafetyCheckAssetById(Long safetyCheckAssetId);

    /**
     * 查询Safety Check Asset列表
     * 
     * @param safetyCheckAsset Safety Check Asset
     * @return Safety Check Asset集合
     */
    public List<SafetyCheckAsset> selectSafetyCheckAssetList(SafetyCheckAsset safetyCheckAsset);

    /**
     * 新增Safety Check Asset
     * 
     * @param safetyCheckAsset Safety Check Asset
     * @return 结果
     */
    public int insertSafetyCheckAsset(SafetyCheckAsset safetyCheckAsset);

    /**
     * 修改Safety Check Asset
     * 
     * @param safetyCheckAsset Safety Check Asset
     * @return 结果
     */
    public int updateSafetyCheckAsset(SafetyCheckAsset safetyCheckAsset);

    /**
     * 删除Safety Check Asset
     * 
     * @param safetyCheckAssetId Safety Check AssetID
     * @return 结果
     */
    public int deleteSafetyCheckAssetById(Long safetyCheckAssetId);

    public int deleteSafetyCheckAssetByCheckId(Long safetyCheckId);

    /**
     * 批量删除Safety Check Asset
     * 
     * @param safetyCheckAssetIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSafetyCheckAssetByIds(String[] safetyCheckAssetIds);
}
