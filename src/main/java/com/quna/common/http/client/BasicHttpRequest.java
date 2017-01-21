package com.quna.common.http.client;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import com.quna.common.http.Certificate;
import com.quna.common.http.HttpMethod;
import com.quna.common.http.HttpRequest;
import com.quna.common.http.HttpTimeOut;
import com.quna.common.http.ProxyRoute;
import com.quna.common.http.RequestUtils;

/**
 * <pre>
 * <b>请求对象</b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2015年12月4日 下午3:41:15
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2015年12月4日 下午3:41:15    252054576@qq.com  new file.
 * </pre>
 */

@SuppressWarnings("serial")
public class BasicHttpRequest implements HttpRequest{
	
	private Charset encoding;	

	private String url;
	
	private HttpMethod httpMethod;
	
	private List<NameValuePair> nameValues;	
	
	private Map<String,String> headers;
	
	private ProxyRoute proxyRoute;
	
	private HttpTimeOut httpTimeOut;			
	
	private HttpEntity httpEntity;
	
	private Certificate certificate;
	
	public BasicHttpRequest(String url,HttpMethod httpMethod){
		this(url,httpMethod,null);
	}
	public BasicHttpRequest(String url,HttpMethod method,ProxyRoute proxy){
		this(url, method , proxy, null,null,null,Charset.forName("utf-8"));
	}
	public BasicHttpRequest(String url,HttpMethod method,ProxyRoute proxyRoute,List<NameValuePair> nameValues, Map<String,String> headers,HttpTimeOut timeout,Charset encoding){
		super();
		
		this.url			= url;
		this.httpMethod		= method;
		this.proxyRoute		= proxyRoute;
		this.nameValues		= nameValues;
		this.headers		= headers;
		if(null == timeout){
			httpTimeOut 	= BasicHttpTimeout.defaultBasicHttpTimeout();
		}else{
			this.httpTimeOut= timeout;
		}
		this.encoding		= encoding;
	}	
	
	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(Charset encoding) {
		this.encoding = encoding;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}
	
	public List<NameValuePair> getNameValues() {
		return nameValues;
	}

	public void setNameValues(List<NameValuePair> nameValues) {
		this.nameValues = nameValues;		
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	
	public HttpEntity getHttpEntity() {
		return httpEntity;
	}

	public void setHttpEntity(HttpEntity httpEntity) {
		this.httpEntity = httpEntity;
	}
	
	public ProxyRoute getProxyRoute() {
		return proxyRoute;
	}

	public void setProxyRoute(ProxyRoute proxyRoute) {
		this.proxyRoute = proxyRoute;
	}

	public HttpTimeOut getHttpTimeOut() {
		return httpTimeOut;
	}

	public void setHttpTimeOut(HttpTimeOut httpTimeOut) {
		this.httpTimeOut = httpTimeOut;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public static Builder create(){
		return new Builder();
	}
	
	
	/**
	 * {@link HttpRequest}
	 * 构造器
	 *
	 *
	 * @author 252054576@qq.com
	 * @date 2016年11月21日  上午10:14:00
	 */
	public static class Builder{
		private String url;
		private HttpMethod method			= HttpMethod.GET;
		private BasicProxyRoute proxy;
		private Charset encoding;
		private List<NameValuePair> nameValues;
		private Map<String,String> headers;
		private HttpEntity httpEntity;
		private Certificate certificate;
		private BasicHttpTimeout timeout	= new BasicHttpTimeout();
		
		public Builder setProxy(BasicProxyRoute proxy){
			this.proxy	= proxy;
			return this;
		}
		
		public Builder setUrl(String url){
			this.url	= url;
			return this;
		}
		
		public Builder setMethod(HttpMethod method){
			this.method	= method;
			return this;
		}
		
		
		public Builder setEncoding(Charset encoding) {
			this.encoding = encoding;
			return this;
		}

		public Builder setNameValues(List<NameValuePair> nameValues) {
			this.nameValues = nameValues;
			return this;
		}

		public Builder setHeaders(Map<String, String> headers) {
			this.headers = headers;
			return this;
		}

		public Builder setTimeout(BasicHttpTimeout timeout) {
			this.timeout = timeout;
			return this;
		}
		
		public Builder setHttpEntity(HttpEntity httpEntity){
			this.httpEntity	= httpEntity;
			return this;
		}

		public Builder setCertificate(Certificate certificate) {
			this.certificate = certificate;
			return this;
		}

		public BasicHttpRequest build(){
			BasicHttpRequest request	= new BasicHttpRequest(url, method, proxy, nameValues,headers,timeout, encoding);
			request.setCertificate(this.certificate);
			request.setHttpEntity(httpEntity);
			return request;
		}
	}

	public HttpRequestBase getHttpRequestBase(){
		HttpRequestBase httpRequestBase	= RequestUtils.getRequest(url, httpMethod, headers, nameValues, encoding);
		if(this.httpEntity != null && httpMethod.equals(HttpMethod.POST)){
			((HttpPost) httpRequestBase).setEntity(this.httpEntity);
		}
		RequestConfig.Builder builder	= RequestConfig.custom().setSocketTimeout(httpTimeOut.getSoTimeOut())
		.setConnectTimeout(httpTimeOut.getConnectTimeOut())
		.setConnectionRequestTimeout(httpTimeOut.getRequestTimeOut())
		.setRelativeRedirectsAllowed(true)
		.setRedirectsEnabled(true)
		.setMaxRedirects(50);
		if(null != this.proxyRoute && this.proxyRoute.isValid()){
			builder.setProxy(this.proxyRoute.getHttpPost());
		}
		httpRequestBase.setConfig(builder.build());
		return httpRequestBase;
	}

	@Override
	public String toString() {
		return "BasicHttpRequest [encoding=" + encoding + ", url=" + url
				+ ", httpMethod=" + httpMethod + ", nameValues=" + nameValues
				+ ", headers=" + headers + ", proxyRoute=" + proxyRoute
				+ ", httpTimeOut=" + httpTimeOut + ", httpEntity=" + httpEntity
				+ "]";
	}	
}