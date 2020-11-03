package com.fanfou.food.domain;

import lombok.Data;

/**
 * @author jzw
 * @date 2020/11/2 08:59
 */
@Data
public class Menu {
    private Long id;
    private String name;
    private Integer number;
    private Double price;
}
