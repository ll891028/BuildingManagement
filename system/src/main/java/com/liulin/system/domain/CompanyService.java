package com.liulin.system.domain;

/**
 * @Author: Liu Lin
 * @Date: 2021/8/15 16:47
 */
public class CompanyService {

    private Long companyServiceId;

    private Long serviceId;

    private Long supplierId;

    private String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getCompanyServiceId() {
        return companyServiceId;
    }

    public void setCompanyServiceId(Long companyServiceId) {
        this.companyServiceId = companyServiceId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
