package com.fanfou.food.dao.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 菜单实体类
 *
 * @author jzw
 * @date 2020/10/27 11:52
 */
@Entity(name = "menu")
@Data
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 菜品名
    private String name;

    // 菜品价格
    private Double price;

    // 数量
    private Integer number;

    // 店铺id
    private Long shopId;
}
