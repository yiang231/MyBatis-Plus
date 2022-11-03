package com.atguigu.mp;

import com.atguigu.mp.entity.User;
import com.atguigu.mp.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	//删除
	@Test
	public void test13() {
//		int delete = userMapper.delete(wrapper);//条件删除
//		int deleteById = userMapper.deleteById();
		int deleteBatchIds = userMapper.deleteBatchIds(Arrays.asList(1, 2, 3));

		Map<String, Object> map = new HashMap<>();
		map.put("age", 23);//根据key=value的形式，等值删除
		int deleteByMap = userMapper.deleteByMap(map);//了解即可 delete from user where age = ?;
	}

	//逻辑删除
	@Test
	public void test14() {
		//userMapper.deleteById(1);
		User user = userMapper.selectById(1);
		user.setAge(190);
		userMapper.updateById(user);
		/*
		* ==>  Preparing: UPDATE user SET name=?, age=?, email=?, update_time=? WHERE id=? AND is_deleted=0
		  ==> Parameters: goushidan(String), 190(Integer), test1@baomidou.com(String), 2022-11-03 10:52:55.007(Timestamp), 1(Long)
		* */
		System.out.println("user = " + user);
		/*
		* ==>  Preparing: UPDATE user SET is_deleted=1 WHERE id=? AND is_deleted=0
		  ==> Parameters: 1(Integer)
		is_deleted = 0 时才会被改为 1，这个字段需要默认值
		* */
		/*
		 * 给字段赋予默认值两种方式
		 * 1、MetaObjectHandler处理器赋予默认值，只有新增或者修改的数据才会有默认值
		 * 2、手动在表结构中进行定义
		 * */
	}

	//条件构造器queryWrapper
	@Test
	public void test15() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("age", 21);//等于  column `列名`  value 值  //WHERE is_deleted=0 AND (age = ?)
//		queryWrapper.ne();//不等于
		queryWrapper.like("name", "i");//模糊查询  //WHERE is_deleted=0 AND (age = ? AND name LIKE ?)  //%i%
		queryWrapper.or().likeLeft("name", "i");//%i	表示条件或or的关系，不写默认为与and
		queryWrapper.or().likeRight("name", "i");//i%	//WHERE is_deleted=0 AND (age = ? AND name LIKE ? OR name LIKE ? OR name LIKE ?)
//		queryWrapper.ge();//大于等于
//		queryWrapper.le();//小于等于
//		queryWrapper.gt();//大于
//		queryWrapper.lt();//小于
//		queryWrapper.isNull();//查询字段为空的
//		queryWrapper.between("age", 1, 34);
//		queryWrapper.notBetween("age", 1, 34);//范围查询
//		queryWrapper.in("age", 21, 12, 190);//WHERE is_deleted=0 AND (age = ? AND name LIKE ? OR name LIKE ? OR name LIKE ? AND age IN (?,?,?))
//		queryWrapper.notIn("age", 21, 12, 190);
		List<User> selectList = userMapper.selectList(queryWrapper);
		System.out.println("selectList = " + selectList);
	}

	@Test
	public void test16() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);
		Integer selectCount = userMapper.selectCount(queryWrapper);
		System.out.println("selectCount = " + selectCount);
	}

	@Test
	public void test17() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);

		//排序
		queryWrapper.orderByAsc("age");
//		queryWrapper.orderByDesc("age");
		List<User> selectList = userMapper.selectList(queryWrapper);//WHERE is_deleted=0 AND (age BETWEEN ? AND ?) ORDER BY age ASC
		selectList.forEach(System.out::println);
	}

	//last追加自定义SQL
	@Test
	public void test18() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);

		queryWrapper.last("limit 1");//只返回一条数据
		queryWrapper.last("limit 2");//并且只能调用一次，调用多次只保留最后一次
		List<User> selectList = userMapper.selectList(queryWrapper);//WHERE is_deleted=0 AND (age BETWEEN ? AND ?) limit 2
		selectList.forEach(System.out::println);
	}

	//last存在SQL注入风险
	@Test
	public void test19() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);

		queryWrapper.last("or 1 = 1");//慎用，存在SQL注入的风险，所有条件都成立	WHERE is_deleted=0 AND (age BETWEEN ? AND ?) or 1 = 1
		List<User> selectList = userMapper.selectList(queryWrapper);//WHERE is_deleted=0 AND (age BETWEEN ? AND ?) limit 2
		selectList.forEach(System.out::println);
	}

	//指定查询列
	@Test
	public void test20() {
		//默认查询所有的列，工作室需要什么查询什么
		//SELECT id,name,age,email,create_time,update_time,version,is_deleted FROM user WHERE is_deleted=0 AND (age BETWEEN ? AND ?) or 1 = 1
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);

		queryWrapper.select("id", "name", "age");//无位置要求
		List<User> selectList = userMapper.selectList(queryWrapper);//WHERE is_deleted=0 AND (age BETWEEN ? AND ?) limit 2
		selectList.forEach(System.out::println);
	}

	//条件删除
	@Test
	public void test21() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);
		userMapper.delete(queryWrapper);
	}

	//条件修改
	@Test
	public void test22() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);

		User user = new User();
		user.setAge(12);
		userMapper.update(user, queryWrapper);
	}

	@Test
	public void test23() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);

		//TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 12
		//结果太多异常
		//User user = userMapper.selectOne(queryWrapper);//查到的数据最多就一行
	}

	//自定义xml文件写复杂的SQL语句
	@Test
	public void test24() {
		List<User> selectAll = userMapper.selectAll();
		selectAll.forEach(System.out::println);
	}
}
