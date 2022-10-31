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

	@Test
	public void test2() {
		User user = new User();
		user.setName("张三");
		user.setAge(30);
		user.setEmail("123@qq.com");

		//添加数据
		int rows = userMapper.insert(user);
		//id 1587062368762974210 长度19的Long型
		System.out.println("rows = " + rows);

		//主键自动回填
		//user = User(id=1587063016820600833, name=张三, age=30, email=123@qq.com)
		System.out.println("user = " + user);
	}
}