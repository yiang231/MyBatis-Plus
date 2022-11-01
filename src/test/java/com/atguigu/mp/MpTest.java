package com.atguigu.mp;

import com.atguigu.mp.entity.User;
import com.atguigu.mp.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

	@Test
	public void test8() {
		User user = new User();

		user.setName("配置乐观锁");
		user.setAge(30);
		user.setEmail("123@qq.com");
		userMapper.insert(user);
		System.out.println("user = " + user);//version=1
	}

	@Test
	public void test9() {
		User user = userMapper.selectById(1587426270780645377L);

		user.setEmail("3131@qq.com");
		userMapper.updateById(user);
		System.out.println("user = " + user);
		/*
		* ==>  Preparing: UPDATE user SET name=?, age=?, email=?, create_time=?, update_time=?, version=? WHERE id=? AND version=? 判断条件中version用来判断version数据是否发生变化，决定是否修改
		==> Parameters: 配置乐观锁(String), 30(Integer), 3131@qq.com(String), 2022-11-01 20:48:11.0(Timestamp), 2022-11-01 20:50:26.48(Timestamp), 2(Integer), 1587426270780645377(Long), 1(Integer)
		*
		* user = User(id=1587426270780645377, name=配置乐观锁, age=30, email=3131@qq.com, createTime=Tue Nov 01 20:48:11 CST 2022, updateTime=Tue Nov 01 20:50:26 CST 2022, version=2)
		* 此时将version手动改为3，查出来的版本号为2，条件不成立，就不会修改，Updates: 0
		* */
	}

	@Test
	public void test10() {
//		//queryWrapper查询条件 仅仅只是条件查询，不可以分页
//		List<User> selectList = userMapper.selectList(queryWrapper);//将每一行查询到的数据转换为User对象
//		//根据id查询
//		User selectById = userMapper.selectById(id);
//		//满足queryWrapper的数据一共多少行
//		Integer selectCount = userMapper.selectCount(queryWrappr);
//		//根据指定条件查询返回一个对象	注意：如果该条件对应多条数据则会报错
//		User selectOne = userMapper.selectOne(queryWrappr);
//		//根据集合查询
//		List<User> selectBatchIds = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
//		//带条件的分页查询    重要
//		IPage<User> selectPage = userMapper.selectPage(page, queryWrapper);
//
//		Map<String, Object> map = new HashMap<>();
//		map.put("age", 20);// 表中的列名
//		map.put("name", "张三");
//		List<User> selectByMap = userMapper.selectByMap(map);//where age = 20 and name = '张三'    （了解）
//
//		List<Map<String, Object>> selectMaps = userMapper.selectMaps(queryWrapper);//将每一行查询到的数据转换为map对象	了解
//
//		IPage<Map<String, Object>> selectMapsPage = userMapper.selectMapsPage(page, queryWrapper);//将每行查询到的数据转换为map对象	了解
	}

	@Test
	public void test11() {
		long pageNum = 1;//当前页
		long pageSize = 3;//每页条数
		Page<User> page = new Page<>(pageNum, pageSize);

		QueryWrapper<User> queryWrapper = null;//查询条件

		Page<User> pageResult = userMapper.selectPage(page, queryWrapper);

		System.out.println(page == pageResult);//true

		List<User> records = pageResult.getRecords();//当前页数据
		long total = pageResult.getTotal();//总记录数
		long current = pageResult.getCurrent();//当前第几条
		long size = pageResult.getSize();//每页多少条

		records.forEach(System.out::println);
	}

	//结果为true表明，传什么返回什么，类似于以下操作
	public Page<User> selectPage(Page<User> page) {
		return page;
	}

	@Test
	public void test12() {//改造后
		long pageNum = 1;//当前页
		long pageSize = 3;//每页条数
		Page<User> pageParma = new Page<>(pageNum, pageSize);
		QueryWrapper<User> queryWrapper = null;//查询条件

		userMapper.selectPage(pageParma, queryWrapper);

		List<User> records = pageParma.getRecords();//当前页数据
		long total = pageParma.getTotal();//总记录数
		long current = pageParma.getCurrent();//当前第几条
		long size = pageParma.getSize();//每页多少条
		long pages = pageParma.getPages();//总页数
		boolean hasNext = pageParma.hasNext();//是否有下一页
		boolean hasPrevious = pageParma.hasPrevious();//是否有上一页

		/*
		* ==>  Preparing: SELECT id,name,age,email,create_time,update_time,version FROM user LIMIT m,n   m = (page-1)*limit = (1-1)*3 = 从第几条开始
		  ==> Parameters: 0(Long), 3(Long)
		* */
		records.forEach(System.out::println);
	}
}
