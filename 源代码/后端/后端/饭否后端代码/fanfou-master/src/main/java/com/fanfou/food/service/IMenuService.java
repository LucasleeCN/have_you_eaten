package com.fanfou.food.service;

import com.fanfou.food.controller.form.AddMenuForm;
import com.fanfou.food.domain.Menu;

import java.util.List;

/**
 * @author jzw
 * @date 2020/10/31 14:14
 */
public interface IMenuService {
    void addMenu(AddMenuForm addMenuForm);
    List<Menu> getAll();
    List<Menu> getMenuByShopId(Long shopId);
}
