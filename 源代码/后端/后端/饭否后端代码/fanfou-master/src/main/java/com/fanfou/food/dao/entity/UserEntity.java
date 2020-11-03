package com.fanfou.food.dao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 用户实体类
 *
 * @author jzw
 * @date 2020/10/27 11:09
 */
@Entity(name = "user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 姓名
    private String name;

    // 电话号码
    private String phone;

    // 地址信息
    private String address;

    private String account;

    // 用户类型
    private String type;

    // 身份证号：商家和骑手使用
    private String idenNum;
}
