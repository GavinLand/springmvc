package com.gavin.spring.ioc;

import java.lang.reflect.Field;
import java.util.Map;

import com.gavin.spring.common.ClassUtil;
/**
 * 
 * @author acer
 *
 */
public class BeanIoc {
	 
	public BeanIoc() {
		super();
	}
	
	
	/**
	 * 获取所有service
	 * @param collection
	 * @param anatClass
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Map<String, Object> addService(Map beanMap,Class anatClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		//0先扫描 包含 ExtService
		//1.获取元素bean ExtAuto 并保存到集合
		//2.注入依赖
		return  ClassUtil.findAllBeanByPackAndAnnt("com", beanMap, anatClass);
	}
	/**
	 * 注入
	 * @throws Exception 
	 */
	public static void fileIoc(Map<String, Object> beanMap,Class anatClass) throws Exception {
		for (String key : beanMap.keySet()) {
			//1.先获取对象
			Object object = beanMap.get(key);
			//2.获取class对象属性
			Class classInfo = object.getClass();
			//3.便利属性
			Field[]  fields= classInfo.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				//判断是否包含注解
				if(fields[i].isAnnotationPresent(anatClass)) {
					//获取注解名
					String FieldName = fields[i].getName();
					Object fieldNameObject = beanMap.get(FieldName);
					if(fieldNameObject==null) {
						throw new Exception("为查询到对应bean name");
					}
					//反射注解到对象中
					Field field= fields[i];
					field.setAccessible(true);
					field.set(object, fieldNameObject);
				}
				
			}
			
		}
		  
		 
		
	}
	 
}
