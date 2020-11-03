package com.fanfou.food.controller;

import com.fanfou.food.domain.ResultData;
import com.fanfou.food.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jzw
 * @date 2020/11/1 10:26
 */
@RestController
@RequestMapping("/shop")
public class ShopController {
    final HttpServletRequest request;
    final IShopService shopService;

    @Autowired
    public ShopController(HttpServletRequest request, IShopService shopService) {
        this.request = request;
        this.shopService = shopService;
    }

    /**
     * 获取所有商家信息
     * @return
     */
    @GetMapping("/all")
    public ResultData getAll() {
        Map<String, Object> data = new HashMap<>();
        data.put("shops", this.shopService.getAllShop());
        return ResultData.success(data);
    }
}
