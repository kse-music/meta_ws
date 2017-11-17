package com.hiekn.meta.conf;

import java.util.Properties;

import com.hiekn.meta.util.BeanUtils;

public final class CommonResource {
	
	private static Properties props = BeanUtils.getBean(Properties.class);

	public static final String BASE_PACKAGE = props.getProperty("base.package");
	public static final boolean SWAGGER_INIT = Boolean.parseBoolean(props.getProperty("swagger.init"));
	public static final String SWAGGER_VERSION = props.getProperty("swagger.version");
	public static final String SWAGGER_TITLE = props.getProperty("swagger.title");
	public static final String SWAGGER_HOST = props.getProperty("swagger.host");
	public static final String SWAGGER_BASE_PATH = props.getProperty("swagger.base.path");

}