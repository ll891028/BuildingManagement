package com.liulin.system.service;

import com.liulin.system.domain.WorkOrder;

import java.util.List;

/**
 * Work OrderService接口
 * 
 * @author liulin
 * @date 2022-02-08
 */
public interface IWorkOrderService 
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
     * 批量删除Work Order
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorkOrderByIds(String ids);

    /**
     * 删除Work Order信息
     * 
     * @param workOrderId Work OrderID
     * @return 结果
     */
    public int deleteWorkOrderById(Long workOrderId);
}
