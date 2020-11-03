package com.fanfou.food.dao.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 商铺实体类
 *
 * @author jzw
 * @date 2020/10/27 11:52
 */
@Entity(name = "shop")
@Data
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 店家名
    private String name;

    private String address;

    private String phone;

    // 店家用户id
    private Long userId;
}
