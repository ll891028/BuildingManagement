package com.liulin.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * attachment对象 attachment
 * 
 * @author liulin
 * @date 2021-08-03
 */
public class Attachment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** attachment_id */
    private Long attachmentId;

    /** type */
    @Excel(name = "type")
    private String type;

    /** attachment_url */
    @Excel(name = "attachment_url")
    private String attachmentUrl;

    /** ext */
    @Excel(name = "ext")
    private String ext;

    /** md5 */
    @Excel(name = "md5")
    private String md5;

    private String fileName;

    private int[] attachmentIds;

    private Long buildingId;

    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public int[] getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(int[] attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setAttachmentId(Long attachmentId)
    {
        this.attachmentId = attachmentId;
    }

    public Long getAttachmentId() 
    {
        return attachmentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAttachmentUrl(String attachmentUrl)
    {
        this.attachmentUrl = attachmentUrl;
    }

    public String getAttachmentUrl() 
    {
        return attachmentUrl;
    }
    public void setExt(String ext) 
    {
        this.ext = ext;
    }

    public String getExt() 
    {
        return ext;
    }
    public void setMd5(String md5) 
    {
        this.md5 = md5;
    }

    public String getMd5() 
    {
        return md5;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("attachmentId", getAttachmentId())
            .append("type", getType())
            .append("attachmentUrl", getAttachmentUrl())
            .append("ext", getExt())
            .append("md5", getMd5())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
