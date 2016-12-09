package com.quna.common.http.client;

import java.io.Serializable;

import org.apache.http.HttpHost;

import com.quna.common.http.ProxyRoute;
import com.quna.common.utils.ObjectUtils;
import com.quna.common.utils.StringUtils;
/**
 * 
 * <pre>
 * <b></b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2014-1-1 上午10:00:01
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2014-01-01 10:00:01     252054576@qq.com
 *         new file.
 * </pre>
 */
public class BasicProxyRoute implements ProxyRoute,Serializable {

    private static final long serialVersionUID = 1L;

    private String host 		= null;

    private int port;
    
    private HttpHost httpHost	= null;    

    
    public BasicProxyRoute(final HttpHost httpHost){
    	super();
    	
    	this.host	= httpHost.getHostName();
    	this.port	= httpHost.getPort();
    	
    	this.httpHost= httpHost;
    }
    /**
     * 构造方法.
     * 
     * @param host
     * @param port
     */
    public BasicProxyRoute(String host, int port) {
        super();
        this.host 		= host;
        this.port 		= port;
        this.httpHost	= new HttpHost(host, port);
    }

    /**
     * 判断代理路由是否有效.
     * 
     * @return
     */
    public boolean isValid() {
        return (StringUtils.hasText(this.host) && !ObjectUtils.isEmpty(this.port));
    }

    /**
     * 转换 HttpHost实例.
     * 
     * @return
     */
    public HttpHost toHttpHost() {
        return new HttpHost(this.host, this.port);
    }

    /**
     * 返回 HOST地址.
     * 
     * @return
     */
    public String getHost() {
        return host;
    }

    /**
     * 返回 端口号.
     * 
     * @return
     */
    public int getPort() {
        return port;
    }

    
    public HttpHost getHttpPost() {
		return httpHost;
	}
    
	@Override
    public String toString() {
        return "host:" + this.host + ",port:" + this.port;
    }

}
