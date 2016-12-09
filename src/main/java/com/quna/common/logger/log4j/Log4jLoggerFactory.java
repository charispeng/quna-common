package com.quna.common.logger.log4j;

import org.apache.log4j.LogManager;

import com.quna.common.logger.Logger;
import com.quna.common.logger.LoggerFactory;

public class Log4jLoggerFactory implements LoggerFactory {
	public Logger create(String type) {
		return new Log4jLogger(LogManager.getLogger(type));
	}
	
	public Logger create(Class<?> type) {
		return new Log4jLogger(LogManager.getLogger(type));
	}
	
	public Logger create(Object object) {
		return new Log4jLogger(LogManager.getLogger(object.getClass()));
	}
}
