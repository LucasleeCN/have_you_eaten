package com.fanfou.food.service.impl;

import com.fanfou.food.dao.entity.OrderEntity;
import com.fanfou.food.dao.repo.OrderRepository;
import com.fanfou.food.dao.repo.ShopRepository;
import com.fanfou.food.domain.CustomOrder;
import com.fanfou.food.domain.RiderOrder;
import com.fanfou.food.exceptions.OrderException;
import com.fanfou.food.service.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jzw
 * @date 2020/10/31 13:19
 */
@Service("OrderService")
public class OrderServiceImpl implements IOrderService {
    final OrderRepository orderRepository;
    final ShopRepository shopRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ShopRepository shopRepository) {
        this.orderRepository = orderRepository;
        this.shopRepository = shopRepository;
    }
    @Override
    public List<CustomOrder> getAllCustomOrders(Long customId) throws OrderException {
        List<OrderEntity> orderEntities = orderRepository.findAllByComsumerId(customId);
        List<CustomOrder> customOrderList = new ArrayList<>();
        for (OrderEntity orderEntity: orderEntities) {
            CustomOrder customOrder = new CustomOrder();
            BeanUtils.copyProperties(orderEntity, customOrder);
            customOrderList.add(customOrder);
        }
        return customOrderList;
    }

    @Override
    public List<RiderOrder> getAllRiderOrder() throws OrderException {
//        List<OrderEntity> orderEntities = orderRepository.findAllByComsumerId();
//        List<RiderOrder> riderOrderList = new ArrayList<>();
//        for (OrderEntity orderEntity: orderEntities) {
//            RiderOrder riderOrder = new RiderOrder();
//            BeanUtils.copyProperties(orderEntity, riderOrder);
//            riderOrder.setProducer(shopRepository.getOne(orderEntity.getBusinessId()).getName());
//            riderOrderList.add(riderOrder);
//        }
//        return riderOrderList;
        return null;
    }

    @Override
    public List<RiderOrder> getAllRiderFinishOrder(Long delivererId) throws OrderException {
        List<OrderEntity> orderEntities = orderRepository.findAllByDelivererId(delivererId);
        List<RiderOrder> riderOrderList = new ArrayList<>();
        for (OrderEntity orderEntity: orderEntities) {
            RiderOrder riderOrder = new RiderOrder();
            BeanUtils.copyProperties(orderEntity, riderOrder);
            riderOrder.setProducer(shopRepository.getOne(orderEntity.getShopId()).getName());
            riderOrderList.add(riderOrder);
        }
        return riderOrderList;
    }
}
