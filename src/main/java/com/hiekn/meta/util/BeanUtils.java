package com.hiekn.meta.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils implements ApplicationContextAware{
	
	private static ApplicationContext ac;
	
	public static Object getBean(String name) {  
		checkApplicationContext();  
		return ac.getBean(name);  
	}  
	
	@SuppressWarnings("unchecked")
	public static  <T> T getBean(String name,Class<T> clazz) {  
		checkApplicationContext();  
		return (T)ac.getBean(name);  
	} 
	
	public static <T> T getBean(Class<T> clazz) {  
		checkApplicationContext();  
		Map<String, T> beanMap = ac.getBeansOfType(clazz);  
		Collection<T> valueSet = beanMap.values();  
		List<T> valueList = new ArrayList<T>(valueSet);  
		return valueList.get(0) ; 
	} 

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ac = applicationContext;
	}
	
	private static void checkApplicationContext() { 
		if (ac == null) { 
			ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");//防止某些人在BeanUtils未初始化时就使用了
//			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");  
		}
	}
}
