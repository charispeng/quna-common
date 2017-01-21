package com.quna.common.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;

import com.quna.common.exception.http.HttpResponseHandleException;
import com.quna.common.http.client.BasicHttpTimeout;
import com.quna.common.http.client.BasicProxyRoute;
import com.quna.common.logger.LogUtil;
import com.quna.common.logger.Logger;

/**
 * <pre>
 * <b>构建 </b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2015年12月14日 下午2:04:14
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2015年12月14日 下午2:04:14    252054576@qq.com  new file.
 * </pre>
 */
public final class HttpClients{
    // http://www.iteye.com/topic/947133
    // http://www.blogjava.net/sxyx2008/archive/2011/07/08/353940.html
    // http://www.nitrohsu.com/http-client-4-3-zhong-https-de-shi-yong-fang-fa.html
    // http://rensanning.iteye.com/blog/1550436
    // http://my.eoe.cn/530696/archive/10540.html
    // http://wallimn.iteye.com/blog/540566
    // http://www.oschina.net/code/piece_full?code=25199
    // HTTPS 协议头.
	public static final String HTTPS 					= "https";

    // 字符集编码, 默认: UTF-8.
    private static final Charset UTF_8 					= Charset.forName("UTF-8");

    // 连接池的最大连接数, 默认: 256.
    private static final int MAX_TO_TAL 				= 512;

    // 每个路由上的默认并发连接数, 默认: 128.
    // 默认连接指定路由的socket连接数
    private static final int DEFAULT_MAX_PERROUTE 		= 128;

    // 回收超时的Socket实例.
    //private static final int TIME_TO_LIVE 			= 600000;

    // Httpclient不使用NoDelay策略.
    // 如果启用了NoDelay策略, httpclient和站点之间传输数据时将会尽可能及时地将发送缓冲区中的数据发送出去、而不考虑网络带宽的利用率,
    // 这个策略适合对实时性要求高的场景.
    // 而禁用了这个策略之后, 数据传输会采用Nagle's algorithm发送数据, 该算法会充分顾及带宽的利用率, 而不是数据传输的实时性.
    private static final boolean TCP_NODELAY 			= false;

    // Socket缓冲区的大小（单位为字节）, 默认是1024 * 1024 * 8		= 8KB.
    private static final int SOCKET_BUFFER_SIZE 		= 1024 * 1024 * 10;

    // Http异常重连次数.
    // private static final int RETRY_TIME 				= 3;

    // HttpClient连接池管理器.
    private static PoolingHttpClientConnectionManager cm = null;

    // 需要特殊控制并发的路由.
    // private static Map<String, HttpHost> hosts		= new HashMap<String, HttpHost>();
    /**
     * 日志记录器.
     */
    protected static Logger LOGGER						= LogUtil.getLoggerFactory(HttpClients.class).create(HttpClients.class);
    
    /**
     * 设置路由检测地址
     */
    public static final ProxyRoute DEFAULT_ROUTE_HOST	= new BasicProxyRoute("www.baidu.com", 80);

    // 进行初始化HttpProxyUtil.
    static {
        _init();
        // 定时监控任务
       ScheduledExecutorService ses						= Executors.newSingleThreadScheduledExecutor();
       ses.scheduleWithFixedDelay(
    		   new Runnable() {			
		            public void run() {
		                if (cm != null) {
		                    try {
		                    	//获取日志信息
		                        PoolStats stats = getPoolStats();
		                        if(LOGGER.isInfoEnabled()){
		                        	LOGGER.info("HttpPool Stats->Max:" + stats.getMax() + ", Available:" + stats.getAvailable() + ", Leased:" + stats.getLeased() + ", Pending:" + stats.getPending());
		                        }
		                        //关闭过期连接
		                        closeExpiredConnections();
		                    } catch (Throwable e) {}
		                }
		            }
            }, 60, 60,TimeUnit.SECONDS);
    }

    /**
     * HTTP 代理辅助工具初始化.
     */
    private static void _init() {
    	Registry<ConnectionSocketFactory> registry	= null;
		try {
			//构建ssl factory
			KeyStore ks								= KeyStore.getInstance(KeyStore.getDefaultType());
			SSLContext context						= SSLContexts.custom().loadTrustMaterial(ks, new TrustStrategy() {			
				public boolean isTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory ssf	= new SSLConnectionSocketFactory(context,NoopHostnameVerifier.INSTANCE);
			
			registry			= RegistryBuilder.<ConnectionSocketFactory>create()
		            	.register("http", PlainConnectionSocketFactory.getSocketFactory())
		                .register("https",ssf)
		                .build();
			
		} catch (KeyStoreException e) {
			LOGGER.error(e);
		} catch (KeyManagementException e) {
			LOGGER.error(e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e);
		}
    	cm 		= new PoolingHttpClientConnectionManager(registry);    	
        //cm 	= new PoolingHttpClientConnectionManager(TIME_TO_LIVE, TimeUnit.MILLISECONDS);
        // 设置连接池的最大连接数.
        cm.setMaxTotal(MAX_TO_TAL);
        // 设置每个路由上的默认并发连接数.
        //cm.setDefaultMaxPerRoute(DEFAULT_MAX_PERROUTE);
        cm.setDefaultMaxPerRoute(DEFAULT_MAX_PERROUTE);
        
        //为具体ping路由设置最大并发连接数2个.
        //连接baidu只最多产生2个路由连接
        HttpHost ping = DEFAULT_ROUTE_HOST.getHttpPost();
        cm.setMaxPerRoute(new HttpRoute(ping), 2);
        
        SocketConfig socketConfig = SocketConfig.custom() 	// 获取默认的SocketConfig配置器.
                .setTcpNoDelay(TCP_NODELAY) 				// 设置NoDelay策略.
                .build();
        cm.setDefaultSocketConfig(socketConfig);

        ConnectionConfig connectionConfig = ConnectionConfig.custom() 	// 获取默认的ConnectionConfig配置器.
                .setCharset(UTF_8) 										// 设置全局默认的字符编码.
                .setBufferSize(SOCKET_BUFFER_SIZE) 						// 设置数据缓冲大小: 10MB
                .build();
        cm.setDefaultConnectionConfig(connectionConfig);
    }
    
    /**
     * 获取默认配置的 HttpClient并指定代理地址.
     * @return
     * @throws HttpResponseHandleException
     */
    public static CloseableHttpClient buildClient(HttpRequest request){
        // 判断设置 建立Socket连接最大等待时间 和 数据下载最大等待时间 是否有效并且必须大于1毫秒.
        // 否则自动采用当前默认配置参数.
    	HttpTimeOut httpTimeOut			= request.getHttpTimeOut();
        Integer soTimeout 				= httpTimeOut.getSoTimeOut();
        Integer connectionTimeout 		= httpTimeOut.getConnectTimeOut();
        RequestConfig requestConfig 	= RequestConfig.custom() 							// 获取默认的RequestConfig配置器.
                .setSocketTimeout(soTimeout) 												// 建立Socket连接最大等待时间（单位毫秒）.
                .setConnectTimeout(connectionTimeout) 										// 数据下载最大等待时间（单位毫秒）.
                .setConnectionRequestTimeout(httpTimeOut.getRequestTimeOut()) 				// 请求连接池分配连接实例最大等待时间（单位毫秒）.
                .setRedirectsEnabled(true)
                .setRelativeRedirectsAllowed(true)
                .setMaxRedirects(50)
                .build();

        org.apache.http.impl.client.HttpClientBuilder clientBuilder = org.apache.http.impl.client.HttpClients.custom() 	// 获取默认的HttpClientBuilder配置器.  
                		.setDefaultRequestConfig(requestConfig)								// 设置关联请求参数配置.
                		.setRedirectStrategy(DefaultRedirectStrategy.INSTANCE)
                		.setConnectionManager(cm);
        // 判断是否需要设置代理参数.        
        if (null != request.getProxyRoute() && request.getProxyRoute().isValid()) {
            HttpHost httpHost = request.getProxyRoute().getHttpPost();
            clientBuilder.setProxy(httpHost);
        }
        // 构建CloseableHttpClient 实例.
        return clientBuilder.build();
    }
    
    
    /**
     * 构建特殊的ssl httpclient
     * @param certificate
     * @return
     * @throws KeyStoreException 
     * @throws IOException 
     * @throws CertificateException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     * @throws UnrecoverableKeyException 
     * @throws HttpResponseHandleException
     */
    public static CloseableHttpClient buildSSLClient(HttpRequest request) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException{
    	//没有设置证书信息
    	if(null == request.getCertificate()){
    		return buildClient(request);
    	}
    	 // 判断设置 建立Socket连接最大等待时间 和 数据下载最大等待时间 是否有效并且必须大于1毫秒.
        // 否则自动采用当前默认配置参数.
    	HttpTimeOut httpTimeOut			= BasicHttpTimeout.defaultBasicHttpTimeout();
        Integer soTimeout 				= httpTimeOut.getSoTimeOut();
        Integer connectionTimeout 		= httpTimeOut.getConnectTimeOut();
        RequestConfig requestConfig 	= RequestConfig.custom() 							// 获取默认的RequestConfig配置器.
                .setSocketTimeout(soTimeout)												// 建立Socket连接最大等待时间（单位毫秒）.
                .setConnectTimeout(connectionTimeout) 										// 数据下载最大等待时间（单位毫秒）.
                .setConnectionRequestTimeout(httpTimeOut.getRequestTimeOut()) 				// 请求连接池分配连接实例最大等待时间（单位毫秒）.
                .setRedirectsEnabled(true)
                .setRelativeRedirectsAllowed(true)
                .setMaxRedirects(50)
                .build();
        
        org.apache.http.impl.client.HttpClientBuilder clientBuilder = org.apache.http.impl.client.HttpClients.custom() 	// 获取默认的HttpClientBuilder配置器.  
        		.setDefaultRequestConfig(requestConfig)																	// 设置关联请求参数配置.
        		;
        
        //JKS
    	KeyStore ks							= null;
    	SSLContext sc						= null;
    	Certificate certificate				= request.getCertificate();
    	if(null != certificate.getCertificateType() && CertificateType.PKCS12.equals(certificate.getCertificateType())){
    		ks								= KeyStore.getInstance(certificate.getCertificateType().name());
    	}else{
    		ks								= KeyStore.getInstance(KeyStore.getDefaultType());
    	}
    	ks.load(certificate.getContent(), certificate.getPassword().toCharArray());
    	if(null != certificate.getCertificateType() && CertificateType.PKCS12.equals(certificate.getCertificateType())){
    		sc 								= SSLContexts.custom().loadKeyMaterial(ks, certificate.getPassword().toCharArray()).build();
    	}else{
    		sc 								= SSLContexts.custom().loadTrustMaterial(ks,TrustSelfSignedStrategy.INSTANCE).build();
    	}
    	SSLConnectionSocketFactory ssf		= new SSLConnectionSocketFactory(sc,NoopHostnameVerifier.INSTANCE);//new String[] { "TLSv1" },null,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    	clientBuilder.setSSLSocketFactory(ssf);
        
        // 判断是否需要设置代理参数.
        if (null != request.getProxyRoute() && request.getProxyRoute().isValid()) {
            HttpHost httpHost = request.getProxyRoute().getHttpPost();
            clientBuilder.setProxy(httpHost);
        }
        // 构建CloseableHttpClient 实例.
        return clientBuilder.build();
    }
    
    /**
     * 关闭连接池中已过期的连接.
     */
    public static void closeExpiredConnections() {
        cm.closeExpiredConnections();
    }
    
    
    /**
     * 获取HttpClient连接池的状态.
     * 
     * @return PoolStats
     */
    public static PoolStats getPoolStats() {
        return cm.getTotalStats();
    }
}
