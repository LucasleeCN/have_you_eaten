package com.fanfou.food.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author jzw
 * @date 2020/11/2 10:07
 */
@Data
public class RiderOrder {
    private String address;
    private Date createTime;
    private String producer;
}
