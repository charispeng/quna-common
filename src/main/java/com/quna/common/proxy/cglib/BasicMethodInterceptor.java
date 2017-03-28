package com.quna.common.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.quna.common.proxy.BasicProxyHolder;
import com.quna.common.proxy.ProxyAfter;
import com.quna.common.proxy.ProxyBefore;
import com.quna.common.proxy.ProxyHolder;
import com.quna.common.proxy.ProxyThrowable;
import com.quna.common.proxy.ProxyUtils;

public class BasicMethodInterceptor implements MethodInterceptor {

	private Object target;
	
	private ProxyHolder proxyHolder;
	
	public BasicMethodInterceptor(Object target){
		this.target				= target;
	}
	
	public BasicMethodInterceptor(Object target,ProxyHolder proxyHolder){
		this(target);
		
		if(null != proxyHolder){
			this.proxyHolder	= proxyHolder;
		}else{
			this.proxyHolder	= new BasicProxyHolder();
		}	
	}
	
	public void setProxyGet(ProxyHolder proxyHolder) {
		this.proxyHolder 		= proxyHolder;
	}

	
	public Object intercept(Object proxyObject, Method method, Object[] args,MethodProxy proxy) throws Throwable {
		if(!ProxyUtils.pointcut(proxyHolder,method)){
			return method.invoke(this.target, args);
		}
		ProxyBefore[] proxyBefores			= proxyHolder.getProxyBefores();
		ProxyAfter[] proxyAfters			= proxyHolder.getProxyAfters();
		ProxyThrowable[] proxyThrowables	= proxyHolder.getProxyThrowables();
		
		if(null != proxyBefores && proxyBefores.length != 0){
			for(ProxyBefore proxyBefore : proxyBefores){
				proxyBefore.before(proxyObject, method, args, this.target);
			}
		}
		Object result	= null;
		try{
			result		= method.invoke(this.target, args);
		}catch(Throwable e){
			if(null != proxyThrowables && proxyThrowables.length != 0){
				for(ProxyThrowable proxyThrowable : proxyThrowables){
					proxyThrowable.throwAfter(proxyObject, method, args, target);
				}
			}			
			throw e;			
		}
		if(null != proxyAfters && proxyAfters.length != 0){
			for(ProxyAfter proxyAfter : proxyAfters){
				proxyAfter.after(proxyObject, method, args, this.target, result);
			}
		}
		return result;
	}
}
