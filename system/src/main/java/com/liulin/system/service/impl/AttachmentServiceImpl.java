package com.liulin.system.service.impl;

import java.util.List;
import com.liulin.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.AttachmentMapper;
import com.liulin.system.domain.Attachment;
import com.liulin.system.service.IAttachmentService;
import com.liulin.common.core.text.Convert;

import javax.naming.directory.Attributes;

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
    public List<Attachment> selectAttachmentByIds(int[] ids) {
        Attachment query = new Attachment();
        query.setAttachmentIds(ids);
        return attachmentMapper.selectAttachmentList(query);
    }
}
