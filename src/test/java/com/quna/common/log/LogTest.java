package com.quna.common.log;

import com.quna.common.logger.LogUtil;
import com.quna.common.logger.Logger;

public class LogTest {
	private static final Logger logger	= LogUtil.getLoggerFactory(LogTest.class).create(LogTest.class);
	
	public static void main(String[] args){
		logger.debug("debug",new Throwable("test"));
		logger.info("info",new Throwable("test"));
		logger.error("error",new Throwable("test"));
		logger.fatal("fatal",new Throwable("test"));
		logger.warn("warn",new Throwable("test"));
	}
}
