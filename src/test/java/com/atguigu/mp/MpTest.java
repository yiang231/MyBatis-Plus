package com.atguigu.mp;

import com.atguigu.mp.entity.User;
import com.atguigu.mp.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MpTest {
	@Resource
	private UserMapper userMapper;

	@Test
	public void test1() {
		//查询所有
		List<User> list = userMapper.selectList(null);
		list.forEach(System.out::println);
	}
}