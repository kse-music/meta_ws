package com.hiekn.meta.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hiekn.meta.bean.UserBean;

public interface UserMapper {
	
	int insert(UserBean userBean);
	int delete(Integer id);
	int update(UserBean userBean);
	UserBean selectByUsername(@Param("username")String username);
	List<UserBean> listByPage(@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
	 
}
