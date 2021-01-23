package com.galen.order.domain;

import com.galen.order.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

//继承JpaRepository来完成对数据库的操作
public interface UserRepository extends JpaRepository<SysUser, Long> {
}


