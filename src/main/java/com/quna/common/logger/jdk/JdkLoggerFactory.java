package com.quna.common.logger.jdk;

import com.quna.common.logger.Logger;
import com.quna.common.logger.LoggerFactory;

public class JdkLoggerFactory implements LoggerFactory{

	private String resourceBundleName;
	
	public static JdkLoggerFactory INSTANCE	= new JdkLoggerFactory(null);
	
	public static synchronized void setJdkLoggerResourceBundleName(String resourceBundleName){
		INSTANCE							= new JdkLoggerFactory(resourceBundleName);
	}
	
	public JdkLoggerFactory(String resourceBundleName){
		this.resourceBundleName				= resourceBundleName;
	}
	
	@Override
	public Logger create(String type) {
		return new JdkLogger(java.util.logging.Logger.getLogger(type,resourceBundleName));
	}

	@Override
	public Logger create(Class<?> type) {
		return new JdkLogger(java.util.logging.Logger.getLogger(type.getName(),resourceBundleName));
	}

	@Override
	public Logger create(Object object) {
		return new JdkLogger(java.util.logging.Logger.getLogger(object.getClass().getName(),resourceBundleName));
	}

}
