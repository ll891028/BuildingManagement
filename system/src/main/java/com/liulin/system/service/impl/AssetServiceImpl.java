package com.liulin.system.service.impl;

import com.liulin.common.constant.UserConstants;
import com.liulin.common.core.text.Convert;
import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.system.domain.Asset;
import com.liulin.system.mapper.AssetMapper;
import com.liulin.system.service.IAssetService;
import com.liulin.system.service.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AssetService业务层处理
 * 
 * @author liulin
 * @date 2021-08-07
 */
@Service
public class AssetServiceImpl implements IAssetService 
{
    @Autowired
    private AssetMapper assetMapper;

    @Autowired
    private IAttachmentService attachmentService;

    /**
     * 查询Asset
     * 
     * @param assetId AssetID
     * @return Asset
     */
    @Override
    public Asset selectAssetById(Long assetId)
    {
        return assetMapper.selectAssetById(assetId);
    }

    /**
     * 查询Asset列表
     * 
     * @param asset Asset
     * @return Asset
     */
    @Override
    public List<Asset> selectAssetList(Asset asset)
    {
        return assetMapper.selectAssetList(asset);
    }

    /**
     * 新增Asset
     * 
     * @param asset Asset
     * @return 结果
     */
    @Override
    public int insertAsset(Asset asset)
    {
        asset.setCreateTime(DateUtils.getNowDate());
        if(StringUtils.isNotEmpty(asset.getAttachmentUrls())){
            String[] attachmentUrls = asset.getAttachmentUrls().split(",");
            String[] originalFileNames = asset.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                    asset.getCreateBy(),asset.getBuildingId(), asset.getCompanyId());
            asset.setAttachmentIds(attachmentIds);
        }
        return assetMapper.insertAsset(asset);
    }

    /**
     * 修改Asset
     * 
     * @param asset Asset
     * @return 结果
     */
    @Override
    public int updateAsset(Asset asset)
    {
        asset.setUpdateTime(DateUtils.getNowDate());
        if(StringUtils.isNotEmpty(asset.getAttachmentUrls())){
            String[] attachmentUrls = asset.getAttachmentUrls().split(",");
            String[] originalFileNames = asset.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                    asset.getUpdateBy(), asset.getBuildingId(),asset.getCompanyId() );
            asset.setAttachmentIds(attachmentIds);
        }else {
            asset.setAttachmentIds("");
        }
        return assetMapper.updateAsset(asset);
    }

    /**
     * 删除Asset对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAssetByIds(String ids)
    {
        return assetMapper.deleteAssetByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除Asset信息
     * 
     * @param assetId AssetID
     * @return 结果
     */
    @Override
    public int deleteAssetById(Long assetId)
    {
        return assetMapper.deleteAssetById(assetId);
    }

    @Override
    public String checkAssetNameUnique(Asset asset) {
        Asset assetTemp = assetMapper.checkAssetNameUnique(asset.getAssetName(),asset.getBuildingId(),asset.getAssetId());
        if(StringUtils.isNotNull(assetTemp)){
            return UserConstants.NAME_UNIQUE;
        }
        return UserConstants.NAME_NOT_UNIQUE;
    }

    @Override
    public int updateAttachment(Asset asset) {
        if(StringUtils.isNotEmpty(asset.getAttachmentUrls())){
            String[] attachmentUrls = asset.getAttachmentUrls().split(",");
            String[] originalFileNames = asset.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                    asset.getCreateBy(),asset.getBuildingId(), asset.getCompanyId());
            asset.setAttachmentIds(attachmentIds);
        }
        return assetMapper.updateAsset(asset);
    }
}
