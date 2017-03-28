package com.quna.common.proxy;

import java.lang.reflect.Modifier;

import com.quna.common.proxy.cglib.CglibProxyFactoryBean;
import com.quna.common.proxy.jdk.JdkProxyFactoryBean;

public class ProxyBeanFactory {
	
	private static final ProxyFactoryBean JDKPROXY_FACTORY_BEAN		= new JdkProxyFactoryBean();
	
	private static final ProxyFactoryBean CGLIBP_PROXY_FACTORY_BEAN	= new CglibProxyFactoryBean();
	
	public static <T> T createBean(Class<T> inter,Object object,ProxyHolder proxyHolder){
		boolean supportCglib	= false;
		try{
			Class.forName("net.sf.cglib.beans.BeanCopier");
			supportCglib		= true;
		}catch(Exception e){}
		if(!inter.isInterface()){
			if(!supportCglib){
				throw new IllegalArgumentException("不支持类代理,请导入cglib包");
			}
			return CGLIBP_PROXY_FACTORY_BEAN.createBean(inter, object, proxyHolder);
		}else{
			if(!supportCglib){
				return JDKPROXY_FACTORY_BEAN.createBean(inter, object, proxyHolder);
			}
			try{
				Class.forName("java.lang.reflect.Executable");
				return JDKPROXY_FACTORY_BEAN.createBean(inter, object, proxyHolder);				
			}catch(Exception e){
				if(object.getClass().isMemberClass() || !Modifier.isPublic(object.getClass().getModifiers())){
					return JDKPROXY_FACTORY_BEAN.createBean(inter, object, proxyHolder);
				}
				return CGLIBP_PROXY_FACTORY_BEAN.createBean(inter, object, proxyHolder);
			}
		}
	}
}