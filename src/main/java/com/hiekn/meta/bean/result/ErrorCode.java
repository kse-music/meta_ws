package com.hiekn.meta.bean.result;

import java.util.Objects;

public enum ErrorCode {

	PARAM_ERROR(30001,"参数解析错误"),
	EXIGST_ERROR(30002,"重复添加"),
	USER_ERROR(30003,"用户不存在"),
	PWD_ERROR(30004,"密码错误"),
	GET_CODE_ERROR(30005,"获取短信验证码失败"),
	MCODE_ERROR(30006,"短信验证码错误"),
	JSON_ERROR(40001,"JSON转换失败"),
	VERIFY_ERROR(50001,"验证失败"),
	USER_RIGHT_ERROR(60001,"权限不足"),
	NOT_ENOUGH(70001,"剩余使用次数不足，请充值"),
	HTTP_ERROR(80001,"HTTP相关错误"),
	SERVICE_ERROR(90000,"服务端内部错误"),
	REMOTE_SERVICE_PARSE_ERROR(90001,"远程数据解析错误"),
	REMOTE_SERVICE_ERROR(90002,"远程服务错误");

	private Integer code;
	private String errorInfo;

	ErrorCode(Integer code,String errorInfo){
		this.code = code ;
		this.errorInfo = errorInfo;
	}

	public int getErrorCode() {
		return code;
	}

	public String getInfo() {
		return toString();
	}

	@Override
	public String toString() {
		return errorInfo;
	}

	public static ErrorCode fromErrorCode(Integer code){
		for (ErrorCode error : ErrorCode.values()) {
			if (Objects.equals(code,error.getErrorCode())) {
				return error;
			}
		}
		return null;
	}

}
