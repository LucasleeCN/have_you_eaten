package com.fanfou.food.domain;

import lombok.Data;

/**
 * @author jzw
 * @date 2020/10/27 14:17
 */
@Data
public class User {
    private Long id;
    private String username;
    private String name;
    private String address;
    private String idenNum;
    private String type;
    private String phone;
}
