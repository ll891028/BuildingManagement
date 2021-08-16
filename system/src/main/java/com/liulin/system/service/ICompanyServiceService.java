package com.liulin.system.service;

import com.liulin.system.domain.CompanyService;

import java.util.List;

/**
 * @Author: Liu Lin
 * @Date: 2021/8/15 16:59
 */
public interface ICompanyServiceService {

    int insertBatch(List<Long> serviceList, Long supplierId);

    List<CompanyService> selectCompanyServiceBySupplierId(Long supplierId);

}
