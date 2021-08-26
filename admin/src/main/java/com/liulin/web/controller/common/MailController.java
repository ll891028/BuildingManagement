package com.liulin.web.controller.common;

import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.core.domain.entity.SysDept;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.framework.web.domain.MailDomain;
import com.liulin.framework.web.service.MailService;
import com.liulin.system.domain.Asset;
import com.liulin.system.domain.Servize;
import com.liulin.system.domain.Supplier;
import com.liulin.system.service.IAssetService;
import com.liulin.system.service.IServizeService;
import com.liulin.system.service.ISupplierService;
import com.liulin.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: LinLiu
 * @Date: 2021/8/19 3:39 下午
 */
@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IAssetService assetService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private IServizeService servizeService;

    @Autowired
    private MailService mailService;

    private String prefix = "mail";

    @GetMapping(value = {"/sendOrder/{supplierId}/{assetIds}/{serviceId}","/sendOrder/{supplierId}/{serviceId}"})
    public String sendOrderEmail(@PathVariable Long supplierId, @PathVariable(required = false) String assetIds,
                                  @PathVariable Long serviceId, ModelMap mmp){
        Supplier supplier = supplierService.selectSupplierById(supplierId);


        SysDept building = sysDeptService.selectDeptById(ShiroUtils.getSysUser().getBuilding().getDeptId());
        Servize servize = servizeService.selectServizeById(serviceId);
        mmp.put("supplier",supplier);
        mmp.put("building",building);
        mmp.put("subject","Work Order:${taskName}");
        String content = "Hi ${companyName},</br>" +
                "</br>" +
                "This is the building management of ${buildingName}(${buildingAddress}). We would like to order your" +
                " " +
                "${serviceName} " +
                "service for ${instruction}. Could you please advise if you are available within ${date}? </br>" +
                "${items}</br>" +
                "background: ${description}</br>" +
                "</br>" +
                "it'll be appreciated if we can get your response asap. Please call ${phoneNo} if you have any " +
                "questions.</br>" +
                "</br>" +
                "Thanks";
        StringBuffer assetItem = new StringBuffer();
        String tempItem = " item: ${assetName} - ${assetBand} - ${assetLevel}</br>";
        if(StringUtils.isNotEmpty(assetIds)){
            for (String assetId : assetIds.split(",")) {
                Asset asset = assetService.selectAssetById(Long.valueOf(assetId));
                assetItem.append(tempItem.replace("${assetName}",asset.getAssetName())
                        .replace("${assetBand}",asset.getBrand())
                        .replace("${assetLevel}",asset.getLevelName()));
            }
        }


        content = content
                .replace("${companyName}",supplier.getCompanyName())
                .replace("${buildingName}",building.getDeptName())
                .replace("${buildingAddress}",building.getAddress())
                .replace("${serviceName}",servize.getServiceName())
                .replace("${items}",assetItem.toString())
                .replace("${phoneNo}", ShiroUtils.getSysUser().getPhonenumber());
        mmp.put("content",content);
        return prefix + "/sendOrder";
    }

    @RequestMapping("sendEmail")
    @ResponseBody
    public AjaxResult sendEmail(MailDomain mailDomain) throws Exception {
        mailService.sendMail(mailDomain);
        return  AjaxResult.success();
    }
}
