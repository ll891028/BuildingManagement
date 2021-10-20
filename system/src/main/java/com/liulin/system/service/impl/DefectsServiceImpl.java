package com.liulin.system.service.impl;

import java.util.List;
import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.system.service.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.DefectsMapper;
import com.liulin.system.domain.Defects;
import com.liulin.system.service.IDefectsService;
import com.liulin.common.core.text.Convert;

/**
 * Defects RegisterService业务层处理
 * 
 * @author liulin
 * @date 2021-10-15
 */
@Service
public class DefectsServiceImpl implements IDefectsService 
{
    @Autowired
    private DefectsMapper defectsMapper;

    @Autowired
    private IAttachmentService attachmentService;

    /**
     * 查询Defects Register
     * 
     * @param defectId Defects RegisterID
     * @return Defects Register
     */
    @Override
    public Defects selectDefectsById(Long defectId)
    {
        return defectsMapper.selectDefectsById(defectId);
    }

    /**
     * 查询Defects Register列表
     * 
     * @param defects Defects Register
     * @return Defects Register
     */
    @Override
    public List<Defects> selectDefectsList(Defects defects)
    {
        return defectsMapper.selectDefectsList(defects);
    }

    /**
     * 新增Defects Register
     * 
     * @param defects Defects Register
     * @return 结果
     */
    @Override
    public int insertDefects(Defects defects)
    {
        defects.setCreateTime(DateUtils.getNowDate());
        String[] attachmentUrls = defects.getAttachmentUrls().split(",");
        String[] originalFileNames = defects.getOriginalFileNames().split(",");
        String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,defects.getCreateBy());
        defects.setAttachmentIds(attachmentIds);
        return defectsMapper.insertDefects(defects);
    }

    /**
     * 修改Defects Register
     * 
     * @param defects Defects Register
     * @return 结果
     */
    @Override
    public int updateDefects(Defects defects)
    {
        defects.setUpdateTime(DateUtils.getNowDate());
        if(StringUtils.isNotEmpty(defects.getAttachmentUrls())){
            String[] attachmentUrls = defects.getAttachmentUrls().split(",");
            String[] originalFileNames = defects.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                    defects.getUpdateBy());
            defects.setAttachmentIds(attachmentIds);
        }

        return defectsMapper.updateDefects(defects);
    }

    /**
     * 删除Defects Register对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDefectsByIds(String ids)
    {
        return defectsMapper.deleteDefectsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除Defects Register信息
     * 
     * @param defectId Defects RegisterID
     * @return 结果
     */
    @Override
    public int deleteDefectsById(Long defectId)
    {
        return defectsMapper.deleteDefectsById(defectId);
    }

    @Override
    public int updateDefectsAttachment(Defects defects) {
        if(StringUtils.isNotEmpty(defects.getAttachmentUrls())){
            String[] attachmentUrls = defects.getAttachmentUrls().split(",");
            String[] originalFileNames = defects.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,defects.getCreateBy());
            defects.setAttachmentIds(attachmentIds);
        }
        return defectsMapper.updateDefects(defects);
    }
}
