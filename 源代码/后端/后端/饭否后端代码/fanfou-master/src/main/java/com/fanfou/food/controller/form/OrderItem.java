package com.fanfou.food.controller.form;

import lombok.Data;

/**
 * @author jzw
 * @date 2020/11/2 16:32
 */
@Data
public class OrderItem {
    private Long menuId;
    private Integer count;
}
