package com.liulin.system.service.impl;

import com.liulin.common.constant.UserConstants;
import com.liulin.common.core.text.Convert;
import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.system.domain.Servize;
import com.liulin.system.mapper.ServizeMapper;
import com.liulin.system.service.IAttachmentService;
import com.liulin.system.service.IServizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * serviceService业务层处理
 * 
 * @author liulin
 * @date 2021-08-09
 */
@Service
public class ServizeServiceImpl implements IServizeService 
{

    private static String[] SERVICE_NAMES = {"Electrical","Plumbing","Fire System",
            "Mechanical","Pest Control","Lift","Cleaning","Swimming Pool","IT/Security","Garbage Chute"};

    @Autowired
    private ServizeMapper servizeMapper;

    @Autowired
    private IAttachmentService attachmentService;

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
        if(StringUtils.isNotEmpty(servize.getAttachmentUrls())){
            String[] attachmentUrls = servize.getAttachmentUrls().split(",");
            String[] originalFileNames = servize.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,
                    servize.getCreateBy(),null,servize.getCompanyId());
            servize.setAttachmentIds(attachmentIds);
        }
        return servizeMapper.insertServize(servize);
    }

    /**
     * 修改service¬
     * 
     * @param servize service
     * @return 结果
     */
    @Override
    public int updateServize(Servize servize)
    {
        servize.setUpdateTime(DateUtils.getNowDate());
        if(StringUtils.isNotEmpty(servize.getAttachmentUrls())){
            String[] attachmentUrls = servize.getAttachmentUrls().split(",");
            String[] originalFileNames = servize.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,servize.getUpdateBy(),
                    null,servize.getCompanyId() );
            servize.setAttachmentIds(attachmentIds);
        }else {
            servize.setAttachmentIds("");
        }
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int generateDefaultService(Long companyId, String createBy) {
        for (String serviceName : SERVICE_NAMES) {
            Servize saver = new Servize();
            saver.setCompanyId(companyId);
            saver.setServiceName(serviceName);
            saver.setStatus(1);
            saver.setCreateBy(createBy);
            this.insertServize(saver);
        }
        return 1;
    }

    @Override
    public String checkServiceNameUnique(Long companyId, String serviceName,Long serviceId) {
        if(StringUtils.isNotNull(servizeMapper.checkServiceNameUnique(companyId,serviceName,serviceId))){
            return UserConstants.NAME_UNIQUE;
        }
        return UserConstants.NAME_NOT_UNIQUE;
    }

    @Override
    public int updateAttachment(Servize servize) {
        if(StringUtils.isNotEmpty(servize.getAttachmentUrls())){
            String[] attachmentUrls = servize.getAttachmentUrls().split(",");
            String[] originalFileNames = servize.getOriginalFileNames().split(",");
            String attachmentIds = attachmentService.insertAttachments(attachmentUrls,originalFileNames,servize.getCreateBy(), null,servize.getCompanyId() );
            servize.setAttachmentIds(attachmentIds);
        }
        return servizeMapper.updateServize(servize);
    }
}
