package com.hiekn.meta.service.impl;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hiekn.meta.bean.UserBean;
import com.hiekn.meta.bean.result.ErrorCodes;
import com.hiekn.meta.bean.result.RestData;
import com.hiekn.meta.dao.UserMapper;
import com.hiekn.meta.exception.ServiceException;
import com.hiekn.meta.service.UserService;

@Service
public class UserServcieImpl implements UserService{
	
	private static Logger log = LogManager.getLogger(UserServcieImpl.class);

	@Resource
	private  UserMapper userMapper;

	@Override
	public UserBean login(String username, String password) {
		UserBean userBean = getByUsername(username);
		if(Objects.isNull(userBean)){
			throw ServiceException.newInstance(ErrorCodes.USER_NOT_FOUND_ERROR);
		}
		return userBean;
	}

	@Override
	public UserBean get(Integer id){
		log.info("用log4j2替代system out输出，部署到生产环境需把控制台等级调至error");
		return null;
	}

	@Override
	public RestData<UserBean> listByPage(Integer pageNo,Integer pageSize) {
		pageNo = (pageNo - 1) * pageSize;
		List<UserBean> list = userMapper.listByPage(pageNo, pageSize);
		Integer count = 33;
		RestData<UserBean> rsData = new RestData<>(list, count);
		return rsData;
	}

	@Override
	public void remove(Integer id){
		userMapper.delete(id);
	}

	@Override
	public UserBean getByUsername(String username) {
		UserBean user = userMapper.selectByUsername(username);
		return user;
	}

	@Override
	public void modify(UserBean userBean) {
		userMapper.update(userBean);
	}

}
