package com.fanfou.food.dao.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;


/**
 * 订单实体类
 *
 * @author jzw
 * @date 2020/10/27 11:52
 */
@Entity(name = "t_order")
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 商家
    @Column(name = "shop_id")
    private Long shopId;

    // 骑手
    @Column(name = "deliverer_id")
    private Long delivererId;

    // 用户
    @Column(name = "comsumer_id")
    private Long comsumerId;

    // 订单创建时间
    private Date time;

}
