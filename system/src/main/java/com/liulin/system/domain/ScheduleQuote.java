package com.liulin.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 schedule_quote
 * 
 * @author liulin
 * @date 2021-09-13
 */
public class ScheduleQuote extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Schedule Quote Id */
    private Long scheduleQuoteId;

    /** Schedule Id */
    @Excel(name = "Schedule Id")
    private Long scheduleId;

    /** Supplier Id */
    @Excel(name = "Supplier Id")
    private Long supplierId;

    /** Quote Price */
    @Excel(name = "Quote Price")
    private BigDecimal quotePrice;

    private String contactPerson;

    private String companyName;

    private String email;

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setScheduleQuoteId(Long scheduleQuoteId)
    {
        this.scheduleQuoteId = scheduleQuoteId;
    }

    public Long getScheduleQuoteId() 
    {
        return scheduleQuoteId;
    }
    public void setScheduleId(Long scheduleId) 
    {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() 
    {
        return scheduleId;
    }
    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }
    public void setQuotePrice(BigDecimal quotePrice) 
    {
        this.quotePrice = quotePrice;
    }

    public BigDecimal getQuotePrice() 
    {
        return quotePrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("scheduleQuoteId", getScheduleQuoteId())
            .append("scheduleId", getScheduleId())
            .append("supplierId", getSupplierId())
            .append("quotePrice", getQuotePrice())
            .toString();
    }
}
