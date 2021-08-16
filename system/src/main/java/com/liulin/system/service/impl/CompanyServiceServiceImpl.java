package com.liulin.system.service.impl;

import com.liulin.system.domain.CompanyService;
import com.liulin.system.mapper.CompanyServiceMapper;
import com.liulin.system.service.ICompanyServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Liu Lin
 * @Date: 2021/8/15 16:59
 */
@Service
public class CompanyServiceServiceImpl implements ICompanyServiceService {

    @Autowired
    private CompanyServiceMapper companyServiceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(List<Long> serviceList, Long supplierId) {
        List<CompanyService> companyServices = new ArrayList<>();
        companyServiceMapper.deleteByCompanyId(supplierId);
        for (Long serviceId : serviceList) {
            CompanyService saver = new CompanyService();
            saver.setServiceId(serviceId);
            saver.setSupplierId(supplierId);
            companyServices.add(saver);
        }
        return companyServiceMapper.insertBatch(companyServices);
    }

    @Override
    public List<CompanyService> selectCompanyServiceBySupplierId(Long supplierId) {
        return companyServiceMapper.selectCompanyServiceBySupplierId(supplierId);
    }


}
