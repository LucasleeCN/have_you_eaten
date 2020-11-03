package com.fanfou.food.dao.repo;

import com.fanfou.food.dao.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author jzw
 * @date 2020/10/27 12:56
 */
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    List<MenuEntity> findAllByShopId(Long shopId);
}
