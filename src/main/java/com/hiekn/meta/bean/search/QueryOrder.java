package com.hiekn.meta.bean.search;

public class QueryOrder {
	public class Order{
		public final static String ASC = "asc";
		public final static String DESC = "desc";
	}
	private String orderField;
	private String orderType;
	
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
}
