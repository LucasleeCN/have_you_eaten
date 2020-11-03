package com.fanfou.food.dao.repo;

import com.fanfou.food.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author jzw
 * @date 2020/10/27 11:19
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * 通过用户名来查找用户
     *
     * @param username 用户名
     * @return 用户实体
     */
    Optional<UserEntity> findUserEntityByUsername(String username);
}
