package com.liulin.system.mapper;

import java.util.List;
import com.liulin.system.domain.Servize;

/**
 * serviceMapper接口
 * 
 * @author liulin
 * @date 2021-08-09
 */
public interface ServizeMapper 
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
     * 删除service
     * 
     * @param serviceId serviceID
     * @return 结果
     */
    public int deleteServizeById(Long serviceId);

    /**
     * 批量删除service
     * 
     * @param serviceIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteServizeByIds(String[] serviceIds);
}
