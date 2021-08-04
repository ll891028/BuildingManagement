package com.liulin.system.service;

import java.util.List;
import com.liulin.system.domain.Attachment;

/**
 * attachmentService接口
 * 
 * @author liulin
 * @date 2021-08-03
 */
public interface IAttachmentService 
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
     * 批量删除attachment
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAttachmentByIds(String ids);

    /**
     * 删除attachment信息
     * 
     * @param attachmentId attachmentID
     * @return 结果
     */
    public int deleteAttachmentById(Long attachmentId);

    public Attachment getAttachmentByMd5(String md5);

    List<Attachment> selectAttachmentByIds(int[] ids);
}
