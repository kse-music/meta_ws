package com.hiekn.meta.exception;

import com.hiekn.meta.bean.result.ErrorCode;

public class ServiceException extends BaseException{
	
	private static final long serialVersionUID = 1L;
	
	public ServiceException(ErrorCode code) {
		super(code);
	}

	public static ServiceException newInstance(){
		return newInstance(ErrorCode.SERVICE_ERROR);
	}
	
	public static ServiceException newInstance(ErrorCode code){
		return new ServiceException(code);
	}

}
