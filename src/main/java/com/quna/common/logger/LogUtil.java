package com.quna.common.logger;

import com.quna.common.logger.commons.CommonsLoggerFactory;
import com.quna.common.logger.console.DefaultLoggerFactory;
import com.quna.common.logger.log4j.Log4jLoggerFactory;


public class LogUtil {
	static {}

	public static LoggerFactory getLoggerFactory(Object obj) {
		LoggerFactory loggerFactory = null;

		ClassLoader current 		= Thread.currentThread().getContextClassLoader();
		current						= null == current ? obj.getClass().getClassLoader() : current;

		if (loggerFactory == null) {
			try {
				Class.forName("org.apache.log4j.LogManager", false, current);
				loggerFactory 		= new Log4jLoggerFactory();
			} catch (ClassNotFoundException e) {
			}
		}
		if (loggerFactory == null) {
			try {
				Class.forName("org.apache.commons.logging.Log", false, current);
				loggerFactory 		= new CommonsLoggerFactory();
			} catch (ClassNotFoundException e) {
			}
		}
		//if (loggerFactory == null) {
		//	loggerFactory			= com.quna.common.logger.jdk.JdkLoggerFactory.INSTANCE;
		//}
		if (loggerFactory == null){
			loggerFactory 			= DefaultLoggerFactory.getDefault();
		}
		return loggerFactory;
	}
}
