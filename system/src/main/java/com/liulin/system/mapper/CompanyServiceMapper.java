package com.liulin.system.mapper;

import com.liulin.system.domain.Asset;
import com.liulin.system.domain.CompanyService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AssetMapper接口
 * 
 * @author liulin
 * @date 2021-08-07
 */
public interface CompanyServiceMapper
{
    int insertBatch(List<CompanyService> list);
}
