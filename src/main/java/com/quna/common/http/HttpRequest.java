package com.quna.common.http;

import java.io.Serializable;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpRequestBase;

public interface HttpRequest extends Serializable{
	
	/**
	 * 返回获取远程请求的方法
	 * PUT,GET,POST,HEAD,DELETE
	 * @return
	 */
	HttpMethod getHttpMethod();
	
	/**
	 * 获取请求的url地址
	 * @return
	 */
	String getUrl();
	
	/**
	 * 获取编码
	 * @return
	 */
	Charset getEncoding();
	
	/**
	 * 获取代理路由
	 * @return
	 */
	ProxyRoute getProxyRoute();
	
	/**
	 * 获取请求实体
	 * @return
	 */
	HttpEntity getHttpEntity();
	
	/**
	 * 获取连接超时信息
	 * @return
	 */
	HttpTimeOut getHttpTimeOut();
	
	
	/**
	 * 获取请求证书信息
	 * @return
	 */
	Certificate getCertificate();
	
	/**
	 * 将httpRequest转成HttpRequestBase
	 * @return
	 */
	HttpRequestBase getHttpRequestBase();
}
