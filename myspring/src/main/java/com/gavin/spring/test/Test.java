package com.gavin.spring.test;

import java.util.List;

import com.gavin.control.TestControl;
import com.gavin.spring.common.ClassUtil;
import com.gavin.spring.ioc.SpringIoc;

public class Test {

	public static void TestGetAllClass() {
		List<Class<?>> listClass= ClassUtil.getClasses("com");
		for (Class<?> class1 : listClass) {
			System.out.println("获取包下面所有类名"+class1.getName());
		}
	}
	public static void TestGetBean() throws Exception {
		 new SpringIoc();
		TestControl testControl = (TestControl) SpringIoc.contentMap.get("testControl");
		testControl.testService.getName();
	}
	public static void main(String[] args) throws Exception {
		//获取包下面所有类名
//		Test.TestGetAllClass();
//		测试依赖注入
		Test.TestGetBean();
	}
}
