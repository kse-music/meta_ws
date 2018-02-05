package com.hiekn.meta.exception;

import com.hiekn.meta.bean.result.ErrorCodes;

public class ServiceException extends BaseException{
	
	private static final long serialVersionUID = 1L;
	
	public ServiceException(ErrorCodes code) {
		super(code);
	}

	public static ServiceException newInstance(){
		return newInstance(ErrorCodes.SERVICE_ERROR);
	}
	
	public static ServiceException newInstance(ErrorCodes code){
		return new ServiceException(code);
	}

}
