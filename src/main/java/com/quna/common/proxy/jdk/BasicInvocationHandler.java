package com.quna.common.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.quna.common.proxy.BasicProxyHolder;
import com.quna.common.proxy.ProxyAfter;
import com.quna.common.proxy.ProxyBefore;
import com.quna.common.proxy.ProxyHolder;
import com.quna.common.proxy.ProxyThrowable;
import com.quna.common.proxy.ProxyUtils;

public class BasicInvocationHandler implements InvocationHandler {
	
	private Object target;
	
	private ProxyHolder proxyHolder;
	
	public BasicInvocationHandler(Object target,ProxyHolder proxyHolder){
		this.target				= target;
		if(null != proxyHolder){
			this.proxyHolder	= proxyHolder;
		}else{
			this.proxyHolder	= new BasicProxyHolder();
		}
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(!ProxyUtils.pointcut(proxyHolder,method)){
			return method.invoke(this.target, args);
		}		
		ProxyBefore[] proxyBefores			= proxyHolder.getProxyBefores();
		ProxyAfter[] proxyAfters			= proxyHolder.getProxyAfters();
		ProxyThrowable[] proxyThrowables	= proxyHolder.getProxyThrowables();
		
		if(null != proxyBefores && proxyBefores.length != 0){
			for(ProxyBefore proxyBefore : proxyBefores){
				proxyBefore.before(proxy, method, args, this.target);
			}
		}
		Object result	= null;
		try{
			result		= method.invoke(this.target, args);
		}catch(Throwable e){
			if(null != proxyThrowables && proxyThrowables.length != 0){
				for(ProxyThrowable proxyThrowable : proxyThrowables){
					proxyThrowable.throwAfter(proxy, method, args, target);
				}
			}
			throw e;			
		}
		if(null != proxyAfters && proxyAfters.length != 0){
			for(ProxyAfter proxyAfter : proxyAfters){
				proxyAfter.after(proxy, method, args, this.target, result);
			}
		}
		return result;
	}

	
}
