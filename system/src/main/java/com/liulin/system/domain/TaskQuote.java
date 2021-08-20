package com.liulin.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.liulin.common.annotation.Excel;
import com.liulin.common.core.domain.BaseEntity;

/**
 * TaskQuote对象 task_quote
 * 
 * @author liulin
 * @date 2021-08-20
 */
public class TaskQuote extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Task Quote Id */
    private Long taskQuoteId;

    /** Task Id */
    @Excel(name = "Task Id")
    private Long taskId;

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

    public void setTaskQuoteId(Long taskQuoteId)
    {
        this.taskQuoteId = taskQuoteId;
    }

    public Long getTaskQuoteId() 
    {
        return taskQuoteId;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
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
            .append("taskQuoteId", getTaskQuoteId())
            .append("taskId", getTaskId())
            .append("supplierId", getSupplierId())
            .append("quotePrice", getQuotePrice())
            .toString();
    }
}
