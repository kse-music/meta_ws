package com.hiekn.meta.exception;

import com.hiekn.meta.bean.result.ErrorCodes;

/** 
 * 异常基类，各个模块的运行期异常均继承与该类 
 */  
public class BaseException extends RuntimeException {  
   
    private static final long serialVersionUID = 1381325479896057076L;  
  
    private ErrorCodes code;

    public BaseException(ErrorCodes code) {
    	super();
    	this.code = code;
    }
    
	public ErrorCodes getCode() {
		return code;
	}

	public void setCode(ErrorCodes code) {
		this.code = code;
	}


}