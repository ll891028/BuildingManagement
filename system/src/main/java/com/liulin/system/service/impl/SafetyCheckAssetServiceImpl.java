package com.liulin.system.service.impl;

import java.util.List;

import com.liulin.common.utils.StringUtils;
import com.liulin.system.service.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.SafetyCheckAssetMapper;
import com.liulin.system.domain.SafetyCheckAsset;
import com.liulin.system.service.ISafetyCheckAssetService;
import com.liulin.common.core.text.Convert;

/**
 * Safety Check AssetService业务层处理
 * 
 * @author liulin
 * @date 2021-09-29
 */
@Service
public class SafetyCheckAssetServiceImpl implements ISafetyCheckAssetService 
{
    @Autowired
    private SafetyCheckAssetMapper safetyCheckAssetMapper;

    @Autowired
    private IAttachmentService attachmentService;

    /**
     * 查询Safety Check Asset
     * 
     * @param safetyCheckAssetId Safety Check AssetID
     * @return Safety Check Asset
     */
    @Override
    public SafetyCheckAsset selectSafetyCheckAssetById(Long safetyCheckAssetId)
    {
        return safetyCheckAssetMapper.selectSafetyCheckAssetById(safetyCheckAssetId);
    }

    /**
     * 查询Safety Check Asset列表
     * 
     * @param safetyCheckAsset Safety Check Asset
     * @return Safety Check Asset
     */
    @Override
    public List<SafetyCheckAsset> selectSafetyCheckAssetList(SafetyCheckAsset safetyCheckAsset)
    {
        return safetyCheckAssetMapper.selectSafetyCheckAssetList(safetyCheckAsset);
    }

    /**
     * 新增Safety Check Asset
     * 
     * @param safetyCheckAsset Safety Check Asset
     * @return 结果
     */
    @Override
    public int insertSafetyCheckAsset(SafetyCheckAsset safetyCheckAsset)
    {
        return safetyCheckAssetMapper.insertSafetyCheckAsset(safetyCheckAsset);
    }

    /**
     * 修改Safety Check Asset
     * 
     * @param safetyCheckAsset Safety Check Asset
     * @return 结果
     */
    @Override
    public int updateSafetyCheckAsset(SafetyCheckAsset safetyCheckAsset)
    {
        return safetyCheckAssetMapper.updateSafetyCheckAsset(safetyCheckAsset);
    }

    /**
     * 删除Safety Check Asset对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSafetyCheckAssetByIds(String ids)
    {
        return safetyCheckAssetMapper.deleteSafetyCheckAssetByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除Safety Check Asset信息
     * 
     * @param safetyCheckAssetId Safety Check AssetID
     * @return 结果
     */
    @Override
    public int deleteSafetyCheckAssetById(Long safetyCheckAssetId)
    {
        return safetyCheckAssetMapper.deleteSafetyCheckAssetById(safetyCheckAssetId);
    }

    @Override
    public int deleteSafetyCheckAssetByCheckId(Long safetyCheckId) {
        return safetyCheckAssetMapper.deleteSafetyCheckAssetByCheckId(safetyCheckId);
    }

    @Override
    public int updateAssetAttachment(SafetyCheckAsset safetyCheckAsset) {
        if(StringUtils.isNotEmpty(safetyCheckAsset.getAttachmentUrls())){
            String[] attachmentUrls = safetyCheckAsset.getAttachmentUrls().split(",");
            String[] originalFileNames = safetyCheckAsset.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                    safetyCheckAsset.getCreateBy(),safetyCheckAsset.getBuildingId() ,safetyCheckAsset.getCompanyId() );
            safetyCheckAsset.setAttachmentIds(attachmentIds);
        }
        return safetyCheckAssetMapper.updateSafetyCheckAsset(safetyCheckAsset);
    }
}
