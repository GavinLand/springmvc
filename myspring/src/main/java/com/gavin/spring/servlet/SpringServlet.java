package com.gavin.spring.servlet;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.Request;

import com.gavin.annt.ExtRequestMap;
import com.gavin.spring.ioc.SpringIoc;

/**
 * 实现控制器
 * @author acer
 *
 */
public class SpringServlet extends HttpServlet{
	Map<String,Object> URIMap = null;
	Map<String,String> URIMethodMap = null;
	Map<String,Object> controlMap = null;
	
	@Override
	public void init() throws ServletException {
		// 1.获取当前包下的所有的类下controlMap
		try {
			new SpringIoc();
			URIMap = new HashMap<String, Object>();
			URIMethodMap = new HashMap<String, String>();
			controlMap = SpringIoc.contentRequest;
			addUrlMapByURI(controlMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String pageName =handProcess(req, resp);
			extResourceViewResolver(pageName, req, resp);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.doPost(req, resp);
	}
	
	/**
	 * 视图解析
	 * @param pageName
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	private void extResourceViewResolver(String pageName, HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// 根路径
		String prefix = "/";
		String suffix = ".jsp";
		req.getRequestDispatcher(prefix + pageName + suffix).forward(req, res);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	/**
	 * 无参处理
	 * @param URI
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws IOException 
	 */
	private String handProcess(HttpServletRequest req, HttpServletResponse resp) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		String URI = req.getRequestURI();
		Object handProcessObj= URIMap.get(URI);
		String retStr = "";
		if(handProcessObj!=null) {
			String name = (String) URIMethodMap.get(URI);
			Method  method= handProcessObj.getClass().getMethod(name);
			retStr = (String)method.invoke(handProcessObj);
		}else {
			resp.getWriter().println("没有找到请求地址 404");
		}
		return retStr;
	}
	
	//uri地址对应的Object对象
	//地址对应的反射
	private void addUrlMapByURI(Map<String, Object> controlMap) {
		for (Map.Entry<String, Object>  mapSet:controlMap.entrySet() ) {
			String baseUrl = "";
			//uri地址对应的 方法名称
			Class<? extends Object> classInfo= mapSet.getValue().getClass();
			ExtRequestMap declaredAnnotation = classInfo.getAnnotation(ExtRequestMap.class);
			if(declaredAnnotation!=null) {
				baseUrl = declaredAnnotation.value();
			}else {
				continue;
			}
			Method[]  methods= classInfo.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				ExtRequestMap declaredAnnotationMethod = method.getAnnotation(ExtRequestMap.class);
				if(declaredAnnotationMethod!=null) {
					baseUrl = baseUrl+declaredAnnotation.value();
				}
				// springmvc 容器对象 keya:请求地址 ,vlue类
				// springmvc 容器对象 key:请求地址 ,value 方法名称
				URIMap.put(baseUrl, mapSet.getValue());
				URIMethodMap.put(baseUrl,method.getName());
			}
			
		}
	}
}
