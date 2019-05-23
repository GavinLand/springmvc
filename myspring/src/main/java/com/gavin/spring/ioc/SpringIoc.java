package com.gavin.spring.ioc;

import java.util.HashMap;
import java.util.Map;

import com.gavin.annt.ExtAuto;
import com.gavin.annt.ExtControl;
import com.gavin.annt.ExtService;
/**
 * spring装配
 * @author acer
 *
 */
public class SpringIoc {
	public static Map<String,Object> contentMap =null;
	public static Map<String,Object> contentRequest =null;
	public SpringIoc() throws Exception {
		initAuto();
		initIoc();
	}
	public void initAuto() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		contentMap = new HashMap<String, Object>();
		BeanIoc.addService(contentMap, ExtControl.class);
		contentRequest = new HashMap<String, Object>();
		contentRequest.putAll(contentMap);
		BeanIoc.addService(contentMap, ExtService.class);
	}
	
	public void initIoc() throws Exception {
		BeanIoc.fileIoc(contentMap, ExtAuto.class);
	}
	
	 
}
