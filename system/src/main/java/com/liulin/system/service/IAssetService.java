package com.liulin.system.service;

import java.util.List;
import com.liulin.system.domain.Asset;
import com.liulin.system.domain.Defects;

/**
 * AssetService接口
 * 
 * @author liulin
 * @date 2021-08-07
 */
public interface IAssetService 
{
    /**
     * 查询Asset
     * 
     * @param assetId AssetID
     * @return Asset
     */
    public Asset selectAssetById(Long assetId);

    /**
     * 查询Asset列表
     * 
     * @param asset Asset
     * @return Asset集合
     */
    public List<Asset> selectAssetList(Asset asset);

    /**
     * 新增Asset
     * 
     * @param asset Asset
     * @return 结果
     */
    public int insertAsset(Asset asset);

    /**
     * 修改Asset
     * 
     * @param asset Asset
     * @return 结果
     */
    public int updateAsset(Asset asset);

    /**
     * 批量删除Asset
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAssetByIds(String ids);

    /**
     * 删除Asset信息
     * 
     * @param assetId AssetID
     * @return 结果
     */
    public int deleteAssetById(Long assetId);


    String checkAssetNameUnique(Asset asset);

    public int updateAttachment(Asset asset);
}
