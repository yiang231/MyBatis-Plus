package com.atguigu.mp;

import com.atguigu.mp.entity.User;
import com.atguigu.mp.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
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

	@Test
	public void test3() {
//		userMapper.updateById(entity);//根据主键修改
//		userMapper.update(user, queryWrapper);//根据条件进行修改
		User user = userMapper.selectById(1);
		System.out.println("user修改前 = " + user);//此时User被查询出来，所有字段不为空

		user.setName("李四");
		user.setAge(21);
		int rows = userMapper.updateById(user);//这是动态SQL
		System.out.println("rows = " + rows);
		System.out.println("user修改后 = " + user);
		/*所有字段都有值？取决于这个字段是否为空，有就出现
		==>  Preparing: UPDATE user SET name=?, age=?, email=? WHERE id=?
		==> Parameters: 李四(String), 21(Integer), test1@baomidou.com(String), 1(Long)
		* */
	}

	@Test
	public void test4() {
		User user = new User();

		user.setName("goushidan");
		user.setId(1L);
		/*
		==>  Preparing: UPDATE user SET name=? WHERE id=?
		==> Parameters: goushidan(String), 1(Long)
		* */
		System.out.println(userMapper.updateById(user));
	}

	/**
	 * createTime以及updateTime实现在insert或者update时自动赋值填充
	 */
	@Test
	public void test5() {
		User user = new User();

		user.setName("lisi");
		user.setAge(21);
		user.setEmail("213@qq.com");
		user.setCreateTime(new Date());//固定不变
		user.setUpdateTime(new Date());//每次修改就要重新赋值

		userMapper.insert(user);
		System.out.println("user = " + user);

		userMapper.selectById(user.getId());
		System.out.println("user = " + user);

		user.setAge(2);
		user.setUpdateTime(new Date());//每次修改就要重新赋值
		System.out.println("user = " + user);
	}

	@Test
	public void test6() {
		User user = new User();

		user.setName("fuck");
		user.setAge(221);
		user.setEmail("2122223@qq.com");
		//添加完对应的类实现MetaObjectHandler后不需要再写
		/*user.setCreateTime(new Date());
		user.setUpdateTime(new Date());*/
		userMapper.insert(user);
	}

	@Test
	public void test7() {
		User user = new User();

		user.setId(1587113287256948737L);
		user.setAge(2121);

		userMapper.updateById(user);
	}
}