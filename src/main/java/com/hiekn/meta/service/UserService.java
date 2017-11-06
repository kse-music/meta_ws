package com.hiekn.meta.service;

import com.hiekn.meta.bean.UserBean;
import com.hiekn.meta.bean.result.RestData;

public interface UserService {
	
	UserBean login(String username, String password);
	
	UserBean get(Integer id);
	
	UserBean getByUsername(String username);
	
	RestData<UserBean> listByPage(Integer pageNo,Integer pageSize);
	
	void remove(Integer id);
	
	void modify(UserBean userBean);
	
}
