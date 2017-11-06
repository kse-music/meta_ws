package com.hiekn.meta.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.hiekn.meta.service.UserService;

public class UserServiceTest extends TestBase{
	
	@Resource
	private UserService userService;
	
	@Test
	public void test(){
		Assert.assertSame(userService,userService);
		Assert.assertEquals(1, 12);
//		Assert.assertTrue (2 < 3);
//		Assert.assertFalse(2 > 3);
//		Assert.assertNotNull();
//		Assert.assertNull();
	}
 
}
