package com.hiekn.meta.bean.result;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResp<T> {
	
    public enum ActionStatusMethod {
        OK("OK"),
        FAIL("FAIL");
        private final String name;
        ActionStatusMethod(final String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            return name;
        }
    }
	
    @JsonProperty("ActionStatus")
	private String ActionStatus = ActionStatusMethod.OK.toString();
    @JsonProperty("ErrorCodes")
	private Integer ErrorCode = 0;
    @JsonProperty("ErrorInfo")
	private String ErrorInfo = "";
	private RestData<T> data = null;
	private Long tt;
	
	public RestResp() {	}
	
	public RestResp(Long tt){
		this.tt = tt;
	}
	
	public RestResp(Integer code,String msg,Long tt){
		this.ActionStatus = ActionStatusMethod.FAIL.toString();
		this.ErrorCode = code;
		this.ErrorInfo = msg == null? ErrorCodes.fromErrorCode(code).toString():msg;
		this.tt = tt;
	}
	
	public RestResp(T data, Long tt){
		this(Lists.newArrayList(data),tt);
	}
	
	public RestResp(List<T> data, Long tt){
		this(data==null?new RestData<T>(new ArrayList<T>()):new RestData<T>(data),tt);
	}
	
	public RestResp(List<T> data, Integer count, Long tt){
		this(data,Long.valueOf(count),tt);
	}
	
	public RestResp(List<T> data, Long count, Long tt){
		this(data,tt);
		this.data.setRsCount(count);
	}
	
	public RestResp(RestData<T> data, Long tt){
		this.data = data==null?new RestData<T>(new ArrayList<T>()):data;
		this.tt = tt;
	}

	@JsonIgnore
	public String getActionStatus() {
		return ActionStatus;
	}

	public void setActionStatus(String actionStatus) {
		ActionStatus = actionStatus;
	}
	
	@JsonIgnore
	public Integer getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(Integer errorCode) {
		ErrorCode = errorCode;
	}
	
	@JsonIgnore
	public String getErrorInfo() {
		return ErrorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		ErrorInfo = errorInfo;
	}

	public RestData<T> getData() {
		return data;
	}

	public void setData(RestData<T> data) {
		this.data = data;
	}

	public Long getTt() {
		return tt;
	}

	public void setTt(Long tt) {
		this.tt = tt;
	}
	
}
