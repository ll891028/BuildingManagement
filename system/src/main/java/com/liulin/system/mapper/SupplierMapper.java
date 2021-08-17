package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.Supplier;
import org.apache.ibatis.annotations.Param;

/**
 * supplierMapper接口
 * 
 * @author liulin
 * @date 2021-08-09
 */
public interface SupplierMapper 
{
    /**
     * 查询supplier
     * 
     * @param supplierId supplierID
     * @return supplier
     */
    public Supplier selectSupplierById(Long supplierId);

    /**
     * 查询supplier列表
     * 
     * @param supplier supplier
     * @return supplier集合
     */
    public List<Supplier> selectSupplierList(Supplier supplier);

    /**
     * 新增supplier
     * 
     * @param supplier supplier
     * @return 结果
     */
    public int insertSupplier(Supplier supplier);

    /**
     * 修改supplier
     * 
     * @param supplier supplier
     * @return 结果
     */
    public int updateSupplier(Supplier supplier);

    /**
     * 删除supplier
     * 
     * @param supplierId supplierID
     * @return 结果
     */
    public int deleteSupplierById(Long supplierId);

    /**
     * 批量删除supplier
     * 
     * @param supplierIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSupplierByIds(String[] supplierIds);

    Supplier checkSupplierNameUnique(@Param("companyId") Long companyId,@Param("companyName") String companyName,
                                     @Param("supplierId") Long supplierId);
}
