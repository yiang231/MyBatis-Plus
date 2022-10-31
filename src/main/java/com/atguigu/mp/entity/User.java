package com.atguigu.mp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.UUID;

@Data
public class User {
	@TableId(type = IdType.ASSIGN_ID)//第一种 默认主键生成策略Long型，来自twitter的雪花算法，数据库中用bigint类型
//	@TableId(type = IdType.ASSIGN_UUID)//第二种 UUID去掉连接线长度32 实体类用String 数据库中用varchar至少32长度
//	@TableId(type = IdType.INPUT)//第三种 自定义主键
//	@TableId(type = IdType.AUTO)//第四种 类型中的id属性必须是整型
	private Long id;
	private String name;
	private Integer age;
	private String email;

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString());
		//50183492-25a9-42ec-9a78-45c6d2b4a966
		System.out.println(UUID.randomUUID().toString().length());//36
	}
}
