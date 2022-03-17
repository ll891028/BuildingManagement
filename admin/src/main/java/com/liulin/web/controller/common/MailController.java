package com.liulin.web.controller.common;

import com.liulin.common.config.LiulinConfig;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.core.domain.entity.SysDept;
import com.liulin.common.core.domain.entity.SysUser;
import com.liulin.common.core.redis.RedisCache;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.framework.web.domain.MailDomain;
import com.liulin.framework.web.service.MailService;
import com.liulin.system.domain.*;
import com.liulin.system.domain.dto.ResidentDto;
import com.liulin.system.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    private IResidentService residentService;

    @Autowired
    private MailService mailService;

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private IWorkOrderService workOrderService;

    @Autowired
    private RedisCache redisCache;

    private String prefix = "mail";

    @GetMapping(value = {"/sendOrder/{supplierId}/{assetIds}/{serviceId}", "/sendOrder/{supplierId}/{serviceId}"})
    public String sendOrderEmail(@PathVariable Long supplierId, @PathVariable(required = false) String assetIds,
                                 @PathVariable Long serviceId, ModelMap mmp) {
        Supplier supplier = supplierService.selectSupplierById(supplierId);


        SysDept building = sysDeptService.selectDeptById(ShiroUtils.getSysUser().getBuilding().getDeptId());
        Servize servize = servizeService.selectServizeById(serviceId);
        mmp.put("receiver", ShiroUtils.getSysUser().getEmail());
        mmp.put("bcc", supplier.getEmail());
        mmp.put("building", building);
        mmp.put("subject", "Work Order:${taskName}");
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
                "Thanks</br>" +
                ShiroUtils.getLoginName() + "</br>" +
                ShiroUtils.getSysUser().getCompany().getDeptName();
        StringBuffer assetItem = new StringBuffer();
        String tempItem = " item: ${assetName} - ${assetBand} - ${assetLevel}</br>";
        if (StringUtils.isNotEmpty(assetIds)) {
            for (String assetId : assetIds.split(",")) {
                Asset asset = assetService.selectAssetById(Long.valueOf(assetId));
                assetItem.append(tempItem.replace("${assetName}", asset.getAssetName())
                        .replace("${assetBand}", asset.getBrand())
                        .replace("${assetLevel}", asset.getLevelName()));
            }
        }


        content = content
                .replace("${companyName}", supplier.getCompanyName())
                .replace("${buildingName}", building.getDeptName())
                .replace("${buildingAddress}", building.getAddress())
                .replace("${serviceName}", servize.getServiceName())
                .replace("${items}", assetItem.toString())
                .replace("${phoneNo}", ShiroUtils.getSysUser().getPhonenumber());
        mmp.put("content", content);
        return prefix + "/sendOrder";
    }

    @GetMapping(value = {"/sendOrderWithPdf/{taskId}"})
    public String sendOrderEmailWithPdf(@PathVariable Long taskId, ModelMap mmp) {
        Task task = taskService.selectTaskById(taskId);
        Supplier supplier = supplierService.selectSupplierById(task.getOrderSupplierId());
        TaskAsset assetQuery = new TaskAsset();
        assetQuery.setTaskId(taskId);
        Servize servize = servizeService.selectServizeById(task.getServiceId());
        SysDept building = sysDeptService.selectDeptById(ShiroUtils.getSysUser().getBuilding().getDeptId());
        mmp.put("receiver", ShiroUtils.getSysUser().getEmail());
        mmp.put("bcc", supplier.getEmail());
        mmp.put("building", building);
        mmp.put("subject", "Work Order:"+task.getTaskName());
        mmp.put("taskId", taskId);
        String content = "Hi ${companyName},</br>" +
                "</br>" +
                "You have received a work order from " + ShiroUtils.getSysUser().getCompany().getDeptName() + ".\n" +
                "Please see the attached document for more details.</br>" +
                "This is an automatically generated email. To reply, click reply in your email client.";

        content = content
                .replace("${companyName}", supplier.getCompanyName());
        mmp.put("content", content);
        SysUser sysUser = ShiroUtils.getSysUser();
        WorkOrder workOrderTmp = workOrderService.selectWorkOrderByTaskId(taskId);
        if(workOrderTmp==null){
            int count = 0;
            String media1Path = "";
            String media2Path = "";
//            if (StringUtils.isNotEmpty(task.getAttachmentIds())) {
//                String[] attachmentIds = task.getAttachmentIds().split(",");
//                long[] attachmentIdArray =
//                        Arrays.asList(attachmentIds).stream().mapToLong(Long::parseLong).toArray();
//                List<Attachment> attachments = attachmentService.selectAttachmentByIds(attachmentIdArray);
//                for (Attachment attachment : attachments) {
//                    if (count == 3) {
//                        break;
//                    }
//                    if (FileTypeUtils.getFileTypeByExt(attachment.getExt()).equals("image")) {
//                        String filePathPrefix = LiulinConfig.getProfile() + "/aws/";
//                        String filePath = AwsFileUtils.amazonS3DownloadingByUrl(attachment.getAttachmentUrl(), filePathPrefix);
//                        if (count == 1) {
//                            media1Path = filePath;
//                        } else {
//                            media2Path = filePath;
//                        }
//                        count++;
//                    }
//                }
//            }
            Date now = new Date();
//            String workOrderNumber = WorkOrder.genWorkOrderNum();
            WorkOrder workOrder = new WorkOrder();
            workOrder.setDueBy(task.getTimeScheduled());
            workOrder.setPriority(task.getPriorityStr());
            workOrder.setAssignedTo(supplier.getCompanyName() + " " + supplier.getContactNumber());
            workOrder.setServiceName(servize.getServiceName());
            workOrder.setServiceId(servize.getServiceId());
            workOrder.setContactNumber(sysUser.getPhonenumber());
            workOrder.setDescription(task.getDescription());
//            String logoPath = AwsFileUtils.amazonS3DownloadingByUrl(building.getLogo(), LiulinConfig.getProfile() + "/aws/tempImg/");
//        String media1 = AwsFileUtils.amazonS3DownloadingByUrl(media1Url, LiulinConfig.getProfile()+"/aws/tempImg/");
//        String media2 = AwsFileUtils.amazonS3DownloadingByUrl(media2Url, LiulinConfig.getProfile()+"/aws/tempImg/");
            workOrder.setMedia1(media1Path);
            workOrder.setMedia2(media2Path);
            workOrder.setSpnLogo(building.getLogo());
//            workOrder.setSpnLogo(logoPath);
            workOrder.setSpnName(building.getDeptName());
            workOrder.setSpnAddress(building.getAddress());
            workOrder.setMangerName(sysUser.getLoginName());
            workOrder.setContactEmail(sysUser.getEmail());
            workOrder.setCreateTime(now);
            workOrder.setBuildingId(building.getDeptId());
            workOrder.setUserId(sysUser.getUserId());
            workOrder.setTaskName(task.getTaskName());
//            workOrder.setPdfPath(LiulinConfig.getProfile() + "/aws/pdf/" + workOrderNumber + ".pdf");
            workOrder.setSpn(building.getSpn());
            workOrder.setCompanyName(ShiroUtils.getSysUser().getCompany().getDeptName());
            workOrder.setSupplierName(supplier.getCompanyName());
            workOrder.setSupplierContactNumber(supplier.getContactNumber());
            workOrder.setTaskId(taskId);
            workOrder.setEventType(task.getTaskType());
            workOrderService.insertWorkOrder(workOrder);
            mmp.put("attachmentUrl", workOrder.getPdfPath());
            mmp.put("fileName", workOrder.getWorkOrderNo() + ".pdf");

        }else{
            int count = 0;
            String media1Path = "";
            String media2Path = "";
//            if (StringUtils.isNotEmpty(task.getAttachmentIds())) {
//                String[] attachmentIds = task.getAttachmentIds().split(",");
//                long[] attachmentIdArray =
//                        Arrays.asList(attachmentIds).stream().mapToLong(Long::parseLong).toArray();
//                List<Attachment> attachments = attachmentService.selectAttachmentByIds(attachmentIdArray);
//                for (Attachment attachment : attachments) {
//                    if (count == 3) {
//                        break;
//                    }
//                    if (FileTypeUtils.getFileTypeByExt(attachment.getExt()).equals("image")) {
//                        String filePathPrefix = LiulinConfig.getProfile() + "/aws/";
//                        String filePath = AwsFileUtils.amazonS3DownloadingByUrl(attachment.getAttachmentUrl(), filePathPrefix);
//                        if (count == 1) {
//                            media1Path = filePath;
//                        } else {
//                            media2Path = filePath;
//                        }
//                        count++;
//                    }
//                }
//            }
            Date now = new Date();
            String workOrderNumber = WorkOrder.genWorkOrderNum();
            WorkOrder workOrder = new WorkOrder();
            workOrder.setWorkOrderId(workOrderTmp.getWorkOrderId());
//            Integer workOrderIndex = redisCache.getCacheObject(building.getDeptId() + ":workOrderIndex");
//            String workOrderIndexStr = "";
//            if (workOrderIndex == null) {
//                redisCache.setCacheObject(building.getDeptId() + ":workOrderIndex", 1, 1, TimeUnit.DAYS);
//                workOrderIndexStr = "0000";
//            } else {
//                workOrderIndexStr = String.format("%04d", workOrderIndex);
////            workOrderIndexStr = String.format("%s%04d", workOrderIndex);
//                workOrderIndex++;
//                redisCache.setCacheObject(building.getDeptId() + ":workOrderIndex", workOrderIndex, 1, TimeUnit.DAYS);
//            }
//            workOrder.setWorkOrderNo(WorkOrder.genWorkOrderNum() + workOrderIndexStr);
            workOrder.setWorkOrderNo("WO-"+workOrder.getWorkOrderId());
            workOrder.setDueBy(task.getTimeScheduled());
            workOrder.setPriority(task.getPriorityStr());
            workOrder.setAssignedTo(supplier.getCompanyName() + " " + supplier.getContactNumber());
            workOrder.setServiceName(servize.getServiceName());
            workOrder.setServiceId(servize.getServiceId());
            workOrder.setContactNumber(sysUser.getPhonenumber());
            workOrder.setDescription(task.getDescription());
//            String logoPath = AwsFileUtils.amazonS3DownloadingByUrl(building.getLogo(), LiulinConfig.getProfile() + "/aws/tempImg/");
            workOrder.setMedia1(media1Path);
            workOrder.setMedia2(media2Path);
//            workOrder.setSpnLogo(logoPath);
            workOrder.setSpnName(building.getDeptName());
            workOrder.setSpnAddress(building.getAddress());
            workOrder.setMangerName(sysUser.getLoginName());
            workOrder.setContactEmail(sysUser.getEmail());
            workOrder.setCreateTime(now);
            workOrder.setBuildingId(building.getDeptId());
            workOrder.setUserId(sysUser.getUserId());
            workOrder.setTaskName(task.getTaskName());
            workOrder.setPdfPath(LiulinConfig.getProfile() + "/aws/pdf/" + workOrderNumber + ".pdf");
            workOrder.setSpn(building.getSpn());
            workOrder.setCompanyName(ShiroUtils.getSysUser().getCompany().getDeptName());
            workOrder.setSupplierName(supplier.getCompanyName());
            workOrder.setSupplierContactNumber(supplier.getContactNumber());
            workOrder.setTaskId(taskId);
            workOrder.setEventType(task.getTaskType());
            workOrderService.updateWorkOrder(workOrder);
            mmp.put("attachmentUrl", workOrder.getPdfPath());
            mmp.put("fileName", workOrderNumber + ".pdf");
        }



        return prefix + "/sendOrderWithPdf";
    }

//    public static void main(String[] args) {
//        Integer workOrderIndex = 1;
//        System.out.println(String.format("%04d", workOrderIndex));
//    }

    @GetMapping(value = {"/sendQuote/{supplierIds}/{assetIds}/{serviceId}", "/sendQuote/{supplierIds}/{serviceId}"})
    public String sendQuoteEmail(@PathVariable String supplierIds, @PathVariable(required = false) String assetIds,
                                 @PathVariable Long serviceId, ModelMap mmp) {
        String[] split = supplierIds.split(",");
        List<Long> supplierIdList = new ArrayList<>();
        Arrays.stream(split).forEach(s -> {
            supplierIdList.add(Long.valueOf(s));
        });
        List<Supplier> suppliers = supplierService.selectSupplierByIds(supplierIdList);

        SysDept building = sysDeptService.selectDeptById(ShiroUtils.getSysUser().getBuilding().getDeptId());
        Servize servize = servizeService.selectServizeById(serviceId);

        mmp.put("receiver", ShiroUtils.getSysUser().getEmail());
        StringBuffer bcc = new StringBuffer();
        suppliers.stream().forEach(supplier -> {
            bcc.append(supplier.getEmail() + ";");
        });
        mmp.put("bcc", bcc.toString());
        mmp.put("supplierIds", supplierIds);
        mmp.put("building", building);
        mmp.put("subject", "Request Quote:${taskName}");
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
                "Thanks</br>" +
                ShiroUtils.getLoginName() + "</br>" +
                ShiroUtils.getSysUser().getCompany().getDeptName();
        StringBuffer assetItem = new StringBuffer();
        String tempItem = " item: ${assetName} - ${assetBand} - ${assetLevel}</br>";
        if (StringUtils.isNotEmpty(assetIds)) {
            for (String assetId : assetIds.split(",")) {
                Asset asset = assetService.selectAssetById(Long.valueOf(assetId));
                assetItem.append(tempItem.replace("${assetName}", asset.getAssetName())
                        .replace("${assetBand}", asset.getBrand())
                        .replace("${assetLevel}", asset.getLevelName()));
            }
        }


        content = content
                .replace("${buildingName}", building.getDeptName())
                .replace("${buildingAddress}", building.getAddress())
                .replace("${serviceName}", servize.getServiceName())
                .replace("${items}", assetItem.toString())
                .replace("${phoneNo}", ShiroUtils.getSysUser().getPhonenumber());
        mmp.put("content", content);
        return prefix + "/sendQuote";
    }

    @GetMapping(value = {"/sendResidentMail"})
    public String sendResidentMail(ResidentDto data, ModelMap mmp) {
        String[] split = data.getResidentIds().split(",");
        List<Long> residentIdList = new ArrayList<>();
        Arrays.stream(split).forEach(s -> {
            residentIdList.add(Long.valueOf(s));
        });

        SysDept building = sysDeptService.selectDeptById(ShiroUtils.getSysUser().getBuilding().getDeptId());
        List<Resident> residents = residentService.selectResidentByIds(residentIdList);

        mmp.put("from", ShiroUtils.getSysUser().getEmail());
        StringBuffer bcc = new StringBuffer();
        residents.stream().forEach(resident -> {
            bcc.append(resident.getEmail() + ";");
        });
        mmp.put("bcc", bcc.toString());
        mmp.put("residentIds", data.getResidentIds());
        mmp.put("building", building);
        mmp.put("content", "");
        return prefix + "/sendResident";
    }

    @RequestMapping("sendEmail")
    @ResponseBody
    public AjaxResult sendEmail(MailDomain mailDomain) throws Exception {
        boolean result = mailService.sendMail(mailDomain);
        if(result){
            return AjaxResult.success();
        }else{
            return AjaxResult.error("Send Email Fail");
        }

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
            String content = mailDomain.getContent().replace("${SupplierName}", supplier.getCompanyName());
            MailDomain mailTemp = new MailDomain();
            BeanUtils.copyProperties(mailDomain, mailTemp);
            mailTemp.setBcc(supplier.getEmail());
            mailTemp.setContent(content);
            mailService.sendMail(mailTemp);
        }

        return AjaxResult.success();
    }

    @PostMapping("sendResidentEmail/{residentIds}")
    @ResponseBody
    public AjaxResult sendResidentEmail(@PathVariable String residentIds, MailDomain mailDomain) throws Exception {
        String[] split = residentIds.split(",");
        List<Long> residentIdList = new ArrayList<>();
        Arrays.stream(split).forEach(s -> {
            residentIdList.add(Long.valueOf(s));
        });

        List<Resident> residents = residentService.selectResidentByIds(residentIdList);
        for (Resident resident : residents) {
            MailDomain mailTemp = new MailDomain();
            BeanUtils.copyProperties(mailDomain, mailTemp);
            mailTemp.setBcc(resident.getEmail());
            mailTemp.setContent(mailDomain.getContent());
            mailService.sendMail(mailTemp);
        }

        return AjaxResult.success();
    }

}
