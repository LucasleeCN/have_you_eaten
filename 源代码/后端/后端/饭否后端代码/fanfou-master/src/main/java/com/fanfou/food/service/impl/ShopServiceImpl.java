package com.fanfou.food.service.impl;

import com.fanfou.food.dao.entity.ShopEntity;
import com.fanfou.food.dao.repo.ShopRepository;
import com.fanfou.food.domain.Shop;
import com.fanfou.food.service.IShopService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jzw
 * @date 2020/11/1 11:34
 */
@Service("ShopService")
public class ShopServiceImpl implements IShopService {
    final ShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Shop> getAllShop() {
        List<Shop> shopList = new ArrayList<>();
        List<ShopEntity> shopEntities = shopRepository.findAll();
        for (ShopEntity shopEntity : shopEntities) {
            Shop shop = new Shop();
            BeanUtils.copyProperties(shopEntity, shop);
            shopList.add(shop);
        }
        return shopList;
    }
}
