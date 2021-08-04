package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.Attachment;

/**
 * attachmentMapper接口
 * 
 * @author liulin
 * @date 2021-08-03
 */
public interface AttachmentMapper 
{
    /**
     * 查询attachment
     * 
     * @param attachmentId attachmentID
     * @return attachment
     */
    public Attachment selectAttachmentById(Long attachmentId);

    /**
     * 查询attachment列表
     * 
     * @param attachment attachment
     * @return attachment集合
     */
    public List<Attachment> selectAttachmentList(Attachment attachment);

    /**
     * 新增attachment
     * 
     * @param attachment attachment
     * @return 结果
     */
    public int insertAttachment(Attachment attachment);

    /**
     * 修改attachment
     * 
     * @param attachment attachment
     * @return 结果
     */
    public int updateAttachment(Attachment attachment);

    /**
     * 删除attachment
     * 
     * @param attachmentId attachmentID
     * @return 结果
     */
    public int deleteAttachmentById(Long attachmentId);

    /**
     * 批量删除attachment
     * 
     * @param attachmentIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAttachmentByIds(String[] attachmentIds);

    public Attachment getAttachmentByMd5(String md5);
}
