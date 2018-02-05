package com.hiekn.meta.exception;

import com.hiekn.meta.bean.result.ErrorCodes;

public class JsonException extends BaseException{
	
	private static final long serialVersionUID = 1L;
	
	public JsonException(ErrorCodes code) {
		super(code);
	}

	public static JsonException newInstance(){
		return newInstance(ErrorCodes.JSON_PARSE_ERROR);
	}
	
	public static JsonException newInstance(ErrorCodes code){
		return new JsonException(code);
	}

}
