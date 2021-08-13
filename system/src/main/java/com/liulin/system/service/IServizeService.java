package com.liulin.system.service;

import java.util.List;
import com.liulin.system.domain.Servize;

/**
 * serviceService接口
 * 
 * @author liulin
 * @date 2021-08-09
 */
public interface IServizeService 
{
    /**
     * 查询service
     * 
     * @param serviceId serviceID
     * @return service
     */
    public Servize selectServizeById(Long serviceId);

    /**
     * 查询service列表
     * 
     * @param servize service
     * @return service集合
     */
    public List<Servize> selectServizeList(Servize servize);

    /**
     * 新增service
     * 
     * @param servize service
     * @return 结果
     */
    public int insertServize(Servize servize);

    /**
     * 修改service
     * 
     * @param servize service
     * @return 结果
     */
    public int updateServize(Servize servize);

    /**
     * 批量删除service
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteServizeByIds(String ids);

    /**
     * 删除service信息
     * 
     * @param serviceId serviceID
     * @return 结果
     */
    public int deleteServizeById(Long serviceId);
}
