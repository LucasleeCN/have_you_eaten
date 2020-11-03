package com.fanfou.food.controller.form;

import lombok.Data;

/**
 * @author jzw
 * @date 2020/11/1 20:06
 */
@Data
public class AddMenuForm {
    private String name;
    private Integer number;
    private Double price;
}
