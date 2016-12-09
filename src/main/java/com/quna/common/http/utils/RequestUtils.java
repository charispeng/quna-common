package com.quna.common.http.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import com.quna.common.http.HttpMethod;
import com.quna.common.utils.ObjectUtils;
import com.quna.common.utils.StringUtils;

/**
 * 请求处理类
 */
public final class RequestUtils {
    // 字符集编码, 默认: UTF-8.
    private static final Charset UTF_8 					= Charset.forName("UTF-8");
	
	// 可接收数据、内容类型:
    // text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
    private static final String ACCEPT 					= "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";

    // 支持的数据压缩算法: gzip,deflate,sdch
    private static final String ACCEPT_ENCODING 		= "gzip,deflate,sdch";

    // 可接收语言内容: zh-CN,zh;q=0.8,en;q=0.6
    private static final String ACCEPT_LANGUAGE 		= "zh-CN,zh;q=0.8,en;q=0.6";

    // 缓存控制: max-age=0
    private static final String CACHE_CONTROL 			= "max-age=0";

    // Socket连接保持方式: keep-alive
    private static final String CONNECTION 				= "keep-alive";

    // 客户端浏览器类型:
    // Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like
    // Gecko) Chrome/33.0.1720.0 Safari/537.36
    private static final String USER_AGENT 				= "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1720.0 Safari/537.36";

	/**
     * 创建HttpRequest请求实例.
     * 
     * @param url 请求的URL地址
     * @param method Http请求的方式
     * @param headers 请求信息头
     * @param params 表单参数列表
     * @param encoding 参数默认编码
     * @return HttpRequestBase
     */
    public static HttpRequestBase getRequest(String url, final HttpMethod method, final Map<String, String> headers,final List<NameValuePair> params, Charset encoding) {
    	if(null == method){
    		throw new IllegalArgumentException("Http request method");
    	}
        HttpRequestBase request = null;
        // 判断当前是为POST 请求, 即构建Post请求.
        if (method.equals(HttpMethod.POST)) {
            request = new HttpPost(url);
            // 判断请求参数列表是否有效, 可用则进行封装请求参数.
            if (!ObjectUtils.isEmpty(params)) {
        		// 判断提供的字符集编码无效, 则使用默认的UTF-8编码.
        		encoding = (null != encoding ? encoding : UTF_8);
                // 对POST请求参数列表进行指定编码;
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, encoding);
                // 绑定POST表单参数.
                ((HttpPost) request).setEntity(formEntity);
            }
        } else {
            // 默认构建GET 请求.
            request = new HttpGet(url);
        }

        // 附加多个自定义的请求消息头.
        attachHeader(request, headers);
        return request;
    }

    /**
     * <pre>
     * 附加多个自定义的请求消息头.
     * 
     * 注：调用者可以覆盖或者添加默认的头信息, 具体以Key<->Value形式, 如果Value为NULL, 则将Key对应的默认头信息移除.
     * 默认配置的请求信息头如下: 
     * Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,**;q=0.8
     * Accept-Encoding:gzip,deflate,sdch
     * Accept-Language:zh-CN,zh;q=0.8
     * Connection:keep-alive
     * Host:www.iteye.com
     * User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1720.0 Safari/537.36
     * </pre>
     * 
     * @param request 请求对象GET/POST.
     * @param headers 请求信息头.
     */
    public static void attachHeader(HttpRequestBase request, Map<String, String> headers) {

        // 设置默认的请求信息头.
        request.setHeader(HttpHeaders.ACCEPT, ACCEPT);
        request.setHeader(HttpHeaders.ACCEPT_ENCODING, ACCEPT_ENCODING);
        request.setHeader(HttpHeaders.ACCEPT_LANGUAGE, ACCEPT_LANGUAGE);
        request.setHeader(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL);
        request.setHeader(HttpHeaders.USER_AGENT, USER_AGENT);
        request.setHeader(HttpHeaders.CONNECTION, CONNECTION);
        request.setHeader(HttpHeaders.HOST, request.getURI().getHost());

        // 判断提供的Header集合有效方可进行添加.
        if (!ObjectUtils.isEmpty(headers)) {
            // 依次将所有的Header设置到请求对象中.
            for (Entry<String, String> entry : headers.entrySet()) {
                attachHeader(request, entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * <pre>
     * 附加自定义的请求消息头.
     * 注：调用者可以覆盖或者添加默认的头信息, 如果headerValue为NULL, 则将headerName对应的默认头信息移除.
     * </pre>
     * 
     * @param request 请求对象GET/POST.
     * @param headerName 头信息Name.
     * @param headerValue 头信息Value.
     */
    public static void attachHeader(HttpRequestBase request, String headerName, String headerValue) {
        // 判断请求对象和头信息的name都有效.
        if (null != request && StringUtils.hasText(headerName)) {
            // 判断头信息的value有效, 则进行添加自定义头信息.
            if (StringUtils.isBlank(headerValue)) {
                request.removeHeaders(headerName);
            }
            // 如果用户设置的属性值为blank, 则代表系统默认设置的Headr将被移除.
            else {
                request.addHeader(headerName, headerValue);
            }
        }
    }
    
    /**
     * 将map参数转化成list参数形式
     * @param map	参数键值对
     * @return		list参数
     */
    public static List<NameValuePair> mapToListNameValues(Map<String,String> map){
    	List<NameValuePair> retList	= new ArrayList<NameValuePair>(map.size());
    	for(String key : map.keySet()){
    		String value	= map.get(key);
    		value = StringUtils.nullSafeString(value);
    		
    		retList.add(new BasicNameValuePair(key, value));
    	}
    	
    	return retList;
    }
}
