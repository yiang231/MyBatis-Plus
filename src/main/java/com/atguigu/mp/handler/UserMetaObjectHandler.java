package com.atguigu.mp.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserMetaObjectHandler implements MetaObjectHandler {
	@Override
	//调用insert时调用，为当前对象某些属性自动赋值
	public void insertFill(MetaObject metaObject) {
		setFieldValByName("createTime", new Date(), metaObject);
		setFieldValByName("updateTime", new Date(), metaObject);
	}

	@Override
	//调用updateById时调用，为当前对象某些属性赋值
	public void updateFill(MetaObject metaObject) {
		setFieldValByName("updateTime", new Date(), metaObject);
	}
}
