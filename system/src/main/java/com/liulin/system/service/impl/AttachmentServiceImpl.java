package com.liulin.system.service.impl;

import com.liulin.common.config.LiulinConfig;
import com.liulin.common.config.ServerConfig;
import com.liulin.common.core.text.Convert;
import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.system.domain.Attachment;
import com.liulin.system.mapper.AttachmentMapper;
import com.liulin.system.service.IAttachmentService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * attachmentService业务层处理
 * 
 * @author liulin
 * @date 2021-08-03
 */
@Service
public class AttachmentServiceImpl implements IAttachmentService
{
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询attachment
     * 
     * @param attachmentId attachmentID
     * @return attachment
     */
    @Override
    public Attachment selectAttachmentById(Long attachmentId)
    {
        return attachmentMapper.selectAttachmentById(attachmentId);
    }

    /**
     * 查询attachment列表
     * 
     * @param attachment attachment
     * @return attachment
     */
    @Override
    public List<Attachment> selectAttachmentList(Attachment attachment)
    {
        return attachmentMapper.selectAttachmentList(attachment);
    }

    /**
     * 新增attachment
     * 
     * @param attachment attachment
     * @return 结果
     */
    @Override
    public int insertAttachment(Attachment attachment)
    {
        attachment.setCreateTime(DateUtils.getNowDate());
        return attachmentMapper.insertAttachment(attachment);
    }

    /**
     * 修改attachment
     * 
     * @param attachment attachment
     * @return 结果
     */
    @Override
    public int updateAttachment(Attachment attachment)
    {
        attachment.setUpdateTime(DateUtils.getNowDate());
        return attachmentMapper.updateAttachment(attachment);
    }

    /**
     * 删除attachment对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAttachmentByIds(String ids)
    {
        return attachmentMapper.deleteAttachmentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除attachment信息
     * 
     * @param attachmentId attachmentID
     * @return 结果
     */
    @Override
    public int deleteAttachmentById(Long attachmentId)
    {
        return attachmentMapper.deleteAttachmentById(attachmentId);
    }

    @Override
    public Attachment getAttachmentByMd5(String md5) {
        return attachmentMapper.getAttachmentByMd5(md5);
    }

    @Override
    public List<Attachment> selectAttachmentByIds(long[] ids) {
        Attachment query = new Attachment();
        query.setAttachmentIds(ids);
        return attachmentMapper.selectAttachmentList(query);
    }

    @Override
    public String insertAttachments(String[] attachmentUrls, String[] originalFileNames, String createBy, Long buildingId, Long companyId) {
        String attachmentIds="";
        if(attachmentUrls!=null && attachmentUrls.length>0){
            for (int i = 0; i < attachmentUrls.length; i++) {
                String attachmentUrl = attachmentUrls[i];
                if(StringUtils.isEmpty(attachmentUrl)){
                    continue;
                }
                Attachment saver = new Attachment();
                saver.setExt(FilenameUtils.getExtension(attachmentUrl));
                saver.setAttachmentUrl(attachmentUrl);
                String prefix = LiulinConfig.getUploadPath();
//                File file =
//                        new File(LiulinConfig.getUploadPath()+StringUtils.substringAfter(attachmentUrl,prefix));
//                String filePath = FileUtils.downloadFromUrl(attachmentUrl, prefix);
//                File file = new File(filePath);
//                String md5 = Md5Utils.getMD5ByFile(file);
//                Attachment attachmentByMd5 = getAttachmentByMd5(md5);
//                if (attachmentByMd5 != null) {
//                    saver.setAttachmentUrl(attachmentByMd5.getAttachmentUrl());
//                }
                saver.setFileName(originalFileNames[i]);
//                saver.setMd5(md5);
                saver.setCreateBy(createBy);
                saver.setBuildingId(buildingId);
                saver.setCompanyId(companyId);
                insertAttachment(saver);
                attachmentIds += saver.getAttachmentId() + ",";
            }

        }
        return attachmentIds;
    }
}
