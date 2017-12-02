package com.hiekn.meta.exception;

import com.hiekn.meta.bean.result.ErrorCode;

public class RestException extends BaseException{
	
	private static final long serialVersionUID = 1L;
	
	public RestException(ErrorCode code) {
		super(code);
	}

	public static RestException newInstance(){
		return newInstance(ErrorCode.PARAM_PARSE_ERROR);
	}
	
	public static RestException newInstance(ErrorCode code){
		return new RestException(code);
	}

}
