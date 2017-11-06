package com.hiekn.meta.exception;

import com.hiekn.meta.bean.result.ErrorCode;

/** 
 * 异常基类，各个模块的运行期异常均继承与该类 
 */  
public class BaseException extends RuntimeException {  
   
    private static final long serialVersionUID = 1381325479896057076L;  
  
    private ErrorCode code;

    public BaseException(ErrorCode code) {
    	super();
    	this.code = code;
    }
    
	public ErrorCode getCode() {
		return code;
	}

	public void setCode(ErrorCode code) {
		this.code = code;
	}


}