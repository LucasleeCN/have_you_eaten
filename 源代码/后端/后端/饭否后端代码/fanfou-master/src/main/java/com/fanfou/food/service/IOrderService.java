package com.fanfou.food.service;

import com.fanfou.food.domain.CustomOrder;
import com.fanfou.food.domain.RiderOrder;
import com.fanfou.food.exceptions.OrderException;

import java.util.List;

/**
 * @author jzw
 * @date 2020/10/31 13:18
 */
public interface IOrderService {
    List<CustomOrder> getAllCustomOrders(Long CustomId) throws OrderException;
    List<RiderOrder> getAllRiderOrder() throws OrderException;
    List<RiderOrder> getAllRiderFinishOrder(Long delivererId) throws OrderException;
}
