package com.hiekn.meta.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)  
@Transactional
@ContextConfiguration({"classpath:applicationContext.xml"})  
public class TestBase {
	
	@Ignore
	@Test
	public void test(){
		
	}
}
