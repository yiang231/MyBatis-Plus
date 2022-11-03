package com.atguigu.mp.mapper;

import com.atguigu.mp.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//BaseMapper经实现了许多单表增删改查
public interface UserMapper extends BaseMapper<User> {
	public List<User> selectAll();
}
