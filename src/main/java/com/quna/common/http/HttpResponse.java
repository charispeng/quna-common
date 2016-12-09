package com.quna.common.http;

import java.io.Closeable;
import java.io.Serializable;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

public interface HttpResponse extends Closeable,Serializable{
	
	/**
	 * 获取返回的httpEntity
	 * @return
	 */
	HttpEntity getEntity();
	
	
	/**
	 * 获取请求返回的头部信息
	 * @return
	 */
	Header[] getHeaders();
	
	
	/**
	 * 获取结果转成字符串格式
	 * @return
	 */
	String getText() throws Exception;
	
	
	/**
	 * 获取结果转成字符串格式
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	String getText(Charset charset) throws Exception;
	
	/**
	 * 获取返回的状态
	 * @return
	 */
	int getStatus();
	
	/**
	 * 获取httpclient框架返回的http response
	 * @return
	 */
	org.apache.http.HttpResponse getHttpResponse();
	
}