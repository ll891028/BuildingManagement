package com.liulin.system.service.impl;

import com.liulin.common.config.LiulinConfig;
import com.liulin.common.core.text.Convert;
import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.file.AwsFileUtils;
import com.liulin.common.utils.pdf.PdfItextUtils;
import com.liulin.system.domain.WorkOrder;
import com.liulin.system.mapper.WorkOrderMapper;
import com.liulin.system.service.IWorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Work OrderService业务层处理
 * 
 * @author liulin
 * @date 2022-02-08
 */
@Service
public class WorkOrderServiceImpl implements IWorkOrderService 
{
    @Autowired
    private WorkOrderMapper workOrderMapper;

    /**
     * 查询Work Order
     * 
     * @param workOrderId Work OrderID
     * @return Work Order
     */
    @Override
    public WorkOrder selectWorkOrderById(Long workOrderId)
    {
        return workOrderMapper.selectWorkOrderById(workOrderId);
    }

    @Override
    public WorkOrder selectWorkOrderByTaskId(Long taskId) {
        return workOrderMapper.selectWorkOrderByTaskId(taskId);
    }

    /**
     * 查询Work Order列表
     * 
     * @param workOrder Work Order
     * @return Work Order
     */
    @Override
    public List<WorkOrder> selectWorkOrderList(WorkOrder workOrder)
    {
        return workOrderMapper.selectWorkOrderList(workOrder);
    }

    /**
     * 新增Work Order
     * 
     * @param workOrder Work Order
     * @return 结果
     */
    @Override
    public int insertWorkOrder(WorkOrder workOrder)
    {
        String timeStr = DateUtils.parseDateToStr("yyyy/MM/dd", workOrder.getCreateTime());
        String keyName="workOrderPdf/"+workOrder.getSpn()+"/"+timeStr+"/"+workOrder.getWorkOrderNo()+".pdf";
        try {
            PdfItextUtils.genWorkOrderPdf(LiulinConfig.getProfile()+"/aws/pdf/"+workOrder.getWorkOrderNo()+".pdf",workOrder.getMangerName(),
                    workOrder.getContactEmail(),workOrder.getContactNumber(),workOrder.getSpn(),workOrder.getSpnLogo(),
                    workOrder.getSpnName(),workOrder.getSpnAddress(),workOrder.getServiceName(),workOrder.getCreateTime(),workOrder.getDueBy(),
                    workOrder.getPriority(),workOrder.getTaskName(),workOrder.getDescription(),workOrder.getMedia1(),workOrder.getMedia2(),workOrder.getWorkOrderNo(),
                    workOrder.getCompanyName(),workOrder.getSupplierContactNumber(),workOrder.getSupplierContactNumber(),
                    workOrder.getSupplierEmail(),keyName);
            workOrder.setPdfPath(AwsFileUtils.getUrl(keyName));
        }catch (Exception ex){
            ex.printStackTrace();
        }

//        workOrder.setCreateTime(DateUtils.getNowDate());
        int i = workOrderMapper.insertWorkOrder(workOrder);
        if(i!=0){
            workOrder.setWorkOrderNo("WO-"+workOrder.getWorkOrderId());
            workOrderMapper.updateWorkOrder(workOrder);
        }
        return i;
    }

    /**
     * 修改Work Order
     * 
     * @param workOrder Work Order
     * @return 结果
     */
    @Override
    public int updateWorkOrder(WorkOrder workOrder)
    {
        String timeStr = DateUtils.parseDateToStr("yyyy/MM/dd", workOrder.getCreateTime());
        String keyName="workOrderPdf/"+workOrder.getSpn()+"/"+timeStr+"/"+workOrder.getWorkOrderNo()+".pdf";
        try {
            PdfItextUtils.genWorkOrderPdf(LiulinConfig.getProfile() + "/aws/pdf/" + workOrder.getWorkOrderNo() + ".pdf", workOrder.getMangerName(),
                    workOrder.getContactEmail(), workOrder.getContactNumber(), workOrder.getSpn(), workOrder.getSpnLogo(),
                    workOrder.getSpnName(), workOrder.getSpnAddress(), workOrder.getServiceName(), workOrder.getCreateTime(), workOrder.getDueBy(),
                    workOrder.getPriority(), workOrder.getTaskName(), workOrder.getDescription(), workOrder.getMedia1(), workOrder.getMedia2(), workOrder.getWorkOrderNo(),
                    workOrder.getCompanyName(), workOrder.getSupplierContactNumber(), workOrder.getSupplierContactNumber(),
                    workOrder.getSupplierEmail(), keyName);
            workOrder.setPdfPath(AwsFileUtils.getUrl(keyName));
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return workOrderMapper.updateWorkOrder(workOrder);
    }

    /**
     * 删除Work Order对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWorkOrderByIds(String ids)
    {
        return workOrderMapper.deleteWorkOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除Work Order信息
     * 
     * @param workOrderId Work OrderID
     * @return 结果
     */
    @Override
    public int deleteWorkOrderById(Long workOrderId)
    {
        return workOrderMapper.deleteWorkOrderById(workOrderId);
    }
}
