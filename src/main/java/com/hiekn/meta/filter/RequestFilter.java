package com.hiekn.meta.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class RequestFilter implements Filter{
	

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		httpRequest.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json;charset=UTF-8");
		//跨域设置,生产环境应该去掉
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");  
		httpResponse.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");  
		httpResponse.addHeader("Access-Control-Allow-Headers", "x-requested-with");  
		httpResponse.addHeader("Access-Control-Max-Age", "3600");
		
		
		chain.doFilter(httpRequest, httpResponse);
	}
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	
	}
	


}
