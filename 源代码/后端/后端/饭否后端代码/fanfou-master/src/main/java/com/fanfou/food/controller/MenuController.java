package com.fanfou.food.controller;

import com.fanfou.food.annotation.UserLoginToken;
import com.fanfou.food.controller.form.AddMenuForm;
import com.fanfou.food.domain.Menu;
import com.fanfou.food.domain.ResultData;
import com.fanfou.food.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jzw
 * @date 2020/10/31 14:13
 */
@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {

    final IMenuService menuService;

    @Autowired
    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/add")
    @UserLoginToken
    public ResultData add(@RequestBody AddMenuForm form) {
        log.info(form.toString());
        menuService.addMenu(form);
        return ResultData.success("添加成功");
    }

    @GetMapping("/all")
    public ResultData getAll() {
        List<Menu> menuList = menuService.getAll();
        return ResultData.success(menuList);
    }

    @GetMapping("/shop/get")
    @UserLoginToken
    public ResultData getMenuByShopId(@RequestParam Long shopId) {
        log.info("shopId: " + shopId);
        List<Menu> menuList = menuService.getMenuByShopId(shopId);
        Map<String, Object> map = new HashMap<>();
        map.put("menus", menuList);
        return ResultData.success(map);
    }
}
