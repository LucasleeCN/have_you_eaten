package com.fanfou.food.dao.repo;

import com.fanfou.food.dao.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jzw
 * @date 2020/10/27 12:58
 */
public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
}
