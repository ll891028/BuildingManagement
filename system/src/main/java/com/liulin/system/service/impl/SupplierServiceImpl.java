package com.liulin.system.service.impl;

import com.liulin.common.constant.UserConstants;
import com.liulin.common.core.text.Convert;
import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.system.domain.Supplier;
import com.liulin.system.mapper.SupplierMapper;
import com.liulin.system.service.IAttachmentService;
import com.liulin.system.service.ICompanyServiceService;
import com.liulin.system.service.ISupplierService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * supplierService业务层处理
 * 
 * @author liulin
 * @date 2021-08-09
 */
@Service
public class SupplierServiceImpl implements ISupplierService 
{
    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private ICompanyServiceService companyServiceService;

    @Autowired
    private IAttachmentService attachmentService;

    /**
     * 查询supplier
     * 
     * @param supplierId supplierID
     * @return supplier
     */
    @Override
    public Supplier selectSupplierById(Long supplierId)
    {
        return supplierMapper.selectSupplierById(supplierId);
    }

    @Override
    public List<Supplier> selectSupplierByIds(List<Long> supplierIds) {
        return supplierMapper.selectSupplierByIds(supplierIds);
    }

    /**
     * 查询supplier列表
     * 
     * @param supplier supplier
     * @return supplier
     */
    @Override
    public List<Supplier> selectSupplierList(Supplier supplier)
    {
        return supplierMapper.selectSupplierList(supplier);
    }

    /**
     * 新增supplier
     * 
     * @param supplier supplier
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSupplier(Supplier supplier)
    {
        supplier.setCreateTime(DateUtils.getNowDate());
        String[] attachmentUrls = supplier.getAttachmentUrls().split(",");
        String[] originalFileNames = supplier.getOriginalFileNames().split(",");
        String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,supplier.getCreateBy(),null ,supplier.getCompanyId());
        supplier.setAttachmentIds(attachmentIds);
        int result = supplierMapper.insertSupplier(supplier);
        if(CollectionUtils.isNotEmpty(supplier.getServiceIds())){
            companyServiceService.insertBatch(supplier.getServiceIds(),supplier.getSupplierId());
        }

        return result;
    }

    /**
     * 修改supplier
     * 
     * @param supplier supplier
     * @return 结果
     */
    @Override
    public int updateSupplier(Supplier supplier)
    {
        supplier.setUpdateTime(DateUtils.getNowDate());
        if(StringUtils.isNotEmpty(supplier.getAttachmentUrls())){
            String[] attachmentUrls = supplier.getAttachmentUrls().split(",");
            String[] originalFileNames = supplier.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                    supplier.getUpdateBy(),null ,supplier.getCompanyId() );
            supplier.setAttachmentIds(attachmentIds);
        }else {
            supplier.setAttachmentIds("");
        }
        companyServiceService.insertBatch(supplier.getServiceIds(),supplier.getSupplierId());
        return supplierMapper.updateSupplier(supplier);
    }

    /**
     * 删除supplier对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSupplierByIds(String ids)
    {
        return supplierMapper.deleteSupplierByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除supplier信息
     * 
     * @param supplierId supplierID
     * @return 结果
     */
    @Override
    public int deleteSupplierById(Long supplierId)
    {
        return supplierMapper.deleteSupplierById(supplierId);
    }

    @Override
    public String checkSupplierNameUnique(Supplier supplier) {
        if(StringUtils.isNotNull(supplierMapper.checkSupplierNameUnique(supplier.getCompanyId(),supplier.getCompanyName(),
                supplier.getSupplierId()))){
            return UserConstants.NAME_UNIQUE;
        }
        return UserConstants.NAME_NOT_UNIQUE;
    }

    @Override
    public int updateAttachment(Supplier supplier) {
        if(StringUtils.isNotEmpty(supplier.getAttachmentUrls())){
            String[] attachmentUrls = supplier.getAttachmentUrls().split(",");
            String[] originalFileNames = supplier.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,supplier.getCreateBy(),null ,supplier.getCompanyId() );
            supplier.setAttachmentIds(attachmentIds);
        }
        return supplierMapper.updateSupplier(supplier);
    }
}
