package com.quna.common.proxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class BasicProxyHolder implements ProxyHolder {

	private Set<Method> pointcutMethods;		//方法切点
	
	private ProxyBefore[] proxyBefores;			//调用之前
	
	private ProxyAfter[] proxyAfters;			//调用之后
	
	private ProxyThrowable[] proxyThrowables;	//异常
	
	private Set<String> pointcutMethodNames;	//方法名切点
	
	public BasicProxyHolder(){}
	
	public BasicProxyHolder(ProxyBefore[] proxyBefores){
		this.proxyBefores		= proxyBefores;
	}
	
	public BasicProxyHolder(ProxyAfter[] proxyAfters){
		this.proxyAfters		= proxyAfters;
	}
	
	public BasicProxyHolder(ProxyThrowable[] proxyThrowables){
		this.proxyThrowables	= proxyThrowables;
	}
	
	public BasicProxyHolder(Set<Method> pointcutMethods){
		this.pointcutMethods	= pointcutMethods;
	}
	
	public BasicProxyHolder(String...pointcutMethodNames){
		if(null != pointcutMethodNames){
			Collections.addAll(this.pointcutMethodNames, pointcutMethodNames);
		}
	}
	
	public BasicProxyHolder(ProxyBefore[] proxyBefores,ProxyAfter[] proxyAfters,ProxyThrowable[] proxyThrowables){
		this.proxyBefores	= proxyBefores;
		this.proxyAfters	= proxyAfters;
		this.proxyThrowables= proxyThrowables;
	}
	
	public BasicProxyHolder(Set<Method> pointcutMethods,ProxyBefore[] proxyBefores,ProxyAfter[] proxyAfters,ProxyThrowable[] proxyThrowables){
		this(proxyBefores,proxyAfters,proxyThrowables);
		this.pointcutMethods= pointcutMethods;
	}

	public ProxyBefore[] getProxyBefores() {
		return proxyBefores;
	}

	public void setProxyBefores(ProxyBefore[] proxyBefores) {
		this.proxyBefores = proxyBefores;
	}

	public ProxyAfter[] getProxyAfters() {
		return proxyAfters;
	}

	public void setProxyAfters(ProxyAfter[] proxyAfters) {
		this.proxyAfters = proxyAfters;
	}

	public ProxyThrowable[] getProxyThrowables() {
		return proxyThrowables;
	}

	public void setProxyThrowables(ProxyThrowable[] proxyThrowables) {
		this.proxyThrowables = proxyThrowables;
	}

	public Set<Method> getPointcutMethods() {
		return pointcutMethods;
	}

	public void setPointcutMethods(Set<Method> pointcutMethods) {
		this.pointcutMethods = pointcutMethods;
	}

	public Set<String> getPointcutMethodNames() {
		return pointcutMethodNames;
	}

	public void setPointcutMethodNames(Set<String> pointcutMethodNames) {
		this.pointcutMethodNames = pointcutMethodNames;
	}

	@Override
	public String toString() {
		return "BasicProxyHolder [pointcutMethods=" + pointcutMethods
				+ ", proxyBefores=" + Arrays.toString(proxyBefores)
				+ ", proxyAfters=" + Arrays.toString(proxyAfters)
				+ ", proxyThrowables=" + Arrays.toString(proxyThrowables)
				+ ", pointcutMethodNames=" + pointcutMethodNames + "]";
	}
}
