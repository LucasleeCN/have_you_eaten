package com.fanfou.food.dao.repo;

import com.fanfou.food.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author jzw
 * @date 2020/10/27 12:57
 */
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByComsumerId(Long customId);
    List<OrderEntity> findAllByDelivererId(Long delivererId);

}
