package com.hiekn.meta.util;

import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;

/**
 * httpClient依赖JerseyClient
 * @author DingHao
 *
 */
public final class HttpUtils {
	
	private static JerseyClient client;
	private static final String acceptContentType = MediaType.APPLICATION_JSON+";"+MediaType.CHARSET_PARAMETER+"=utf-8";
	private static final String requestContentEncode = MediaType.APPLICATION_FORM_URLENCODED+";"+MediaType.CHARSET_PARAMETER+"=utf-8";
	
	static{
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 600000)
		.property(ClientProperties.READ_TIMEOUT, 600000)
		.register(JacksonJsonProvider.class);
		setHttpClient(clientConfig);
	}
	
	/**
	 * 配置client并新建新的client，ClientConfig clientConfig = new ClientConfig();
	 * @author DingHao
	 * @param configuration
	 */
	public static void setHttpClient(final Configuration configuration){
		client = JerseyClientBuilder.createClient(configuration);
	}
	
	/**
	 * 接受接收任意类型返回
	 * @author DingHao
	 * @param url  请求路径
	 * @param query 参数
	 * @return 返回任意字符串
	 */
	public static String sendGet(String url,MultivaluedMap<String,Object> query){
		WebTarget webTarget = parseQueryParams(url,query);
		return webTarget.request().get(String.class);
	}
	
	/**
	 * 返回application/json字符串自动转成实体bean
	 * @author DingHao
	 * @param url  请求路径
	 * @param query 参数
	 * @param cls class类型
	 * @return 实体bean
	 */
	public static <T> T sendGet(String url,MultivaluedMap<String,Object> query,Class<T> cls){
		WebTarget webTarget = parseQueryParams(url,query);
		T bean = webTarget.request(acceptContentType).get(cls);
		return bean;
	}
	
	/**
	 * 返回application/json,自动转list、array...
	 * @author DingHao
	 * @param url  请求路径
	 * @param query 参数
	 * @param hsp 泛型类型
	 * @return 返回集合类型数据List<Object>或Array
	 */
	public static <T> T sendGet(String url,MultivaluedMap<String,Object> query,GenericType<T> hsp){
		WebTarget webTarget = parseQueryParams(url,query);
		T hidtos = webTarget.request(acceptContentType).get(hsp);
		return hidtos;
	}
	
	/**
	 * 接受接收任意类型返回
	 * @author DingHao
	 * @param url  请求路径
	 * @param query get参数
	 * @param post post参数
	 * @return 返回任意字符串
	 */
	public static String sendPost(String url,MultivaluedMap<String,Object> query, MultivaluedMap<String, Object> post){
		WebTarget webTarget = parseQueryParams(url,query);
		return webTarget.request().post(Entity.entity(parsePostParams(post),requestContentEncode),String.class);
	}
	
	/**
	 * 返回application/json字符串自动转成实体bean
	 * @author DingHao
	 * @param url  请求路径
	 * @param query get参数
	 * @param post post参数
	 * @param cls class类型
	 * @return 实体bean
	 */
	public static <T> T sendPost(String url,MultivaluedMap<String,Object> query, MultivaluedMap<String, Object> post,Class<T> cls){
		WebTarget webTarget = parseQueryParams(url,query);
		T bean = webTarget.request(acceptContentType).post(Entity.entity(parsePostParams(post),requestContentEncode),cls);
		return bean;
	}
	
	/**
	 * 返回application/json,自动转list、array...
	 * @author DingHao
	 * @param url  请求路径
	 * @param query get参数
	 * @param post post参数
	 * @param hsp 泛型类型
	 * @return 返回集合类型数据List<Object>或Array
	 */
	public static <T> T sendPost(String url,MultivaluedMap<String,Object> query, MultivaluedMap<String, Object> post,GenericType<T> hsp){
		WebTarget webTarget = parseQueryParams(url,query);
		T hidtos = webTarget.request(acceptContentType).post(Entity.entity(parsePostParams(post),requestContentEncode),hsp);
		return hidtos;
	}
	
	private static WebTarget parseQueryParams(String url,MultivaluedMap<String,Object> query){
		WebTarget webTarget = client.target(url);
		if(query != null && query.size() > 0){
			for (Entry<String, List<Object>> item : query.entrySet()) {
				webTarget = webTarget.queryParam(item.getKey(), item.getValue().size()>0?item.getValue().get(0):null);
			}
		}
		return webTarget;
	}
	private static MultivaluedMap<String,String> parsePostParams(MultivaluedMap<String,Object> post){
		MultivaluedMap<String,String> p = new MultivaluedHashMap<String,String>();
		if(post != null && post.size() > 0){
			for (Entry<String, List<Object>> item : post.entrySet()) {
				p.add(item.getKey(), item.getValue().size()>0?item.getValue().get(0).toString():null);
			}
		}
		return p;
	}
}
