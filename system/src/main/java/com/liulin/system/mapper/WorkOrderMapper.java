package com.liulin.system.mapper;

import com.liulin.system.domain.WorkOrder;

import java.util.List;

/**
 * Work OrderMapper接口
 * 
 * @author liulin
 * @date 2022-02-08
 */
public interface WorkOrderMapper 
{
    /**
     * 查询Work Order
     * 
     * @param workOrderId Work OrderID
     * @return Work Order
     */
    public WorkOrder selectWorkOrderById(Long workOrderId);

    public WorkOrder selectWorkOrderByTaskId(Long taskId);

    /**
     * 查询Work Order列表
     * 
     * @param workOrder Work Order
     * @return Work Order集合
     */
    public List<WorkOrder> selectWorkOrderList(WorkOrder workOrder);

    /**
     * 新增Work Order
     * 
     * @param workOrder Work Order
     * @return 结果
     */
    public int insertWorkOrder(WorkOrder workOrder);

    /**
     * 修改Work Order
     * 
     * @param workOrder Work Order
     * @return 结果
     */
    public int updateWorkOrder(WorkOrder workOrder);

    /**
     * 删除Work Order
     * 
     * @param workOrderId Work OrderID
     * @return 结果
     */
    public int deleteWorkOrderById(Long workOrderId);

    /**
     * 批量删除Work Order
     * 
     * @param workOrderIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorkOrderByIds(String[] workOrderIds);
}
