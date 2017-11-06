package com.hiekn.meta.bean.result;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.common.collect.Lists;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RestData<T> {

	private List<T> rsData;
	private Long rsCount;
	
	public RestData() {}

	public RestData(T data){
		this(Lists.newArrayList(data));
	}
	
	public RestData(List<T> rsData){
		this.rsData = rsData;
	}
	
	public RestData(List<T> rsData, Integer count){
		this(rsData,Long.valueOf(count));
	}
	
	public RestData(List<T> rsData, Long count){
		this(rsData);
		this.rsCount = count;
	}
	
	public List<T> getRsData() {
		return rsData;
	}
	
	public void setRsData(List<T> rsData) {
		this.rsData = rsData;
	}
	
	public Long getRsCount() {
		return rsCount;
	}
	
	public void setRsCount(Long rsCount) {
		this.rsCount = rsCount;
	}
	
}
