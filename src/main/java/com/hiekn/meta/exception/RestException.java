package com.hiekn.meta.exception;

import com.hiekn.meta.bean.result.ErrorCodes;

public class RestException extends BaseException{
	
	private static final long serialVersionUID = 1L;
	
	public RestException(ErrorCodes code) {
		super(code);
	}

	public static RestException newInstance(){
		return newInstance(ErrorCodes.PARAM_PARSE_ERROR);
	}
	
	public static RestException newInstance(ErrorCodes code){
		return new RestException(code);
	}

}
