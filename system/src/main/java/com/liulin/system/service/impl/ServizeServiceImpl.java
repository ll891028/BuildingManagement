package com.liulin.system.service.impl;

import java.util.List;
import com.liulin.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liulin.system.mapper.ServizeMapper;
import com.liulin.system.domain.Servize;
import com.liulin.system.service.IServizeService;
import com.liulin.common.core.text.Convert;

/**
 * serviceService业务层处理
 * 
 * @author liulin
 * @date 2021-08-09
 */
@Service
public class ServizeServiceImpl implements IServizeService 
{
    @Autowired
    private ServizeMapper servizeMapper;

    /**
     * 查询service
     * 
     * @param serviceId serviceID
     * @return service
     */
    @Override
    public Servize selectServizeById(Long serviceId)
    {
        return servizeMapper.selectServizeById(serviceId);
    }

    /**
     * 查询service列表
     * 
     * @param servize service
     * @return service
     */
    @Override
    public List<Servize> selectServizeList(Servize servize)
    {
        return servizeMapper.selectServizeList(servize);
    }

    /**
     * 新增service
     * 
     * @param servize service
     * @return 结果
     */
    @Override
    public int insertServize(Servize servize)
    {
        servize.setCreateTime(DateUtils.getNowDate());
        return servizeMapper.insertServize(servize);
    }

    /**
     * 修改service
     * 
     * @param servize service
     * @return 结果
     */
    @Override
    public int updateServize(Servize servize)
    {
        servize.setUpdateTime(DateUtils.getNowDate());
        return servizeMapper.updateServize(servize);
    }

    /**
     * 删除service对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteServizeByIds(String ids)
    {
        return servizeMapper.deleteServizeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除service信息
     * 
     * @param serviceId serviceID
     * @return 结果
     */
    @Override
    public int deleteServizeById(Long serviceId)
    {
        return servizeMapper.deleteServizeById(serviceId);
    }
}
