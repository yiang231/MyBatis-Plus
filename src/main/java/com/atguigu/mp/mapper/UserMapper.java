package com.atguigu.mp.mapper;

import com.atguigu.mp.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository//BaseMapper经实现了许多单表增删改查
public interface UserMapper extends BaseMapper<User> {
}
