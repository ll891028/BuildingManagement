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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        mmp.put("receiver",ShiroUtils.getSysUser().getEmail());
        mmp.put("bcc",supplier.getEmail());
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
                "Thanks</br>"+
                ShiroUtils.getLoginName()+"</br>"+
                ShiroUtils.getSysUser().getCompany().getDeptName();
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

    @GetMapping(value = {"/sendQuote/{supplierIds}/{assetIds}/{serviceId}","/sendQuote/{supplierIds}/{serviceId}"})
    public String sendQuoteEmail(@PathVariable String supplierIds, @PathVariable(required = false) String assetIds,
                                 @PathVariable Long serviceId, ModelMap mmp){
        String[] split = supplierIds.split(",");
        List<Long> supplierIdList = new ArrayList<>();
        Arrays.stream(split).forEach(s -> {
            supplierIdList.add(Long.valueOf(s));
        });
        List<Supplier> suppliers = supplierService.selectSupplierByIds(supplierIdList);

        SysDept building = sysDeptService.selectDeptById(ShiroUtils.getSysUser().getBuilding().getDeptId());
        Servize servize = servizeService.selectServizeById(serviceId);

        mmp.put("receiver",ShiroUtils.getSysUser().getEmail());
        StringBuffer bcc = new StringBuffer();
        suppliers.stream().forEach(supplier -> {
            bcc.append(supplier.getEmail()+";");
        });
        mmp.put("bcc",bcc.toString());
        mmp.put("supplierIds",supplierIds);
        mmp.put("building",building);
        mmp.put("subject","Request Quote:${taskName}");
        String content = "Hi ${SupplierName},</br>" +
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
                "Thanks</br>"+
                ShiroUtils.getLoginName()+"</br>"+
                ShiroUtils.getSysUser().getCompany().getDeptName();
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
                .replace("${buildingName}",building.getDeptName())
                .replace("${buildingAddress}",building.getAddress())
                .replace("${serviceName}",servize.getServiceName())
                .replace("${items}",assetItem.toString())
                .replace("${phoneNo}", ShiroUtils.getSysUser().getPhonenumber());
        mmp.put("content",content);
        return prefix + "/sendQuote";
    }

    @RequestMapping("sendEmail")
    @ResponseBody
    public AjaxResult sendEmail(MailDomain mailDomain) throws Exception {
        mailService.sendMail(mailDomain);
        return  AjaxResult.success();
    }

    @RequestMapping("sendQuoteEmail/{supplierIds}")
    @ResponseBody
    public AjaxResult sendQuoteEmail(@PathVariable String supplierIds, MailDomain mailDomain) throws Exception {
        String[] split = supplierIds.split(",");
        List<Long> supplierIdList = new ArrayList<>();
        Arrays.stream(split).forEach(s -> {
            supplierIdList.add(Long.valueOf(s));
        });
        List<Supplier> suppliers = supplierService.selectSupplierByIds(supplierIdList);
        for (Supplier supplier : suppliers) {
            String content = mailDomain.getContent().replace("${SupplierName}",supplier.getCompanyName());
            MailDomain mailTemp = new MailDomain();
            BeanUtils.copyProperties(mailDomain,mailTemp);
            mailTemp.setBcc(supplier.getEmail());
            mailTemp.setContent(content);
            mailService.sendMail(mailTemp);
        }

        return  AjaxResult.success();
    }
}
