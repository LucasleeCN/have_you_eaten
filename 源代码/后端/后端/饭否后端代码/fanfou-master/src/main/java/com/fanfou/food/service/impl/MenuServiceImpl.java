package com.fanfou.food.service.impl;

import com.fanfou.food.controller.form.AddMenuForm;
import com.fanfou.food.dao.entity.MenuEntity;
import com.fanfou.food.dao.repo.MenuRepository;
import com.fanfou.food.domain.Menu;
import com.fanfou.food.service.IMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jzw
 * @date 2020/10/31 14:14
 */
@Service("MenuService")
public class MenuServiceImpl implements IMenuService {
    final MenuRepository menuRepository;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void addMenu(AddMenuForm addMenuForm) {
        MenuEntity menuEntity = new MenuEntity();
        BeanUtils.copyProperties(addMenuForm, menuEntity);
        menuRepository.save(menuEntity);
    }

    @Override
    public List<Menu> getAll() {
        List<MenuEntity> menuEntities = menuRepository.findAll();
        List<Menu> menuList = new ArrayList<>();
        for (MenuEntity menuEntity : menuEntities) {
            Menu menu = new Menu();
            BeanUtils.copyProperties(menuEntity, menu);
            menuList.add(menu);
        }
        return menuList;
    }

    @Override
    public List<Menu> getMenuByShopId(Long shopId) {
        List<Menu> res = new ArrayList<>();
        List<MenuEntity> menuEntityList = menuRepository.findAllByShopId(shopId);
        for (MenuEntity menuEntity : menuEntityList) {
            Menu menu = new Menu();
            BeanUtils.copyProperties(menuEntity, menu);
            res.add(menu);
        }
        return res;

    }
}
