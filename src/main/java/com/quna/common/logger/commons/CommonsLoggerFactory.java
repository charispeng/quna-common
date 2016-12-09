package com.quna.common.logger.commons;

import org.apache.commons.logging.impl.LogFactoryImpl;

import com.quna.common.logger.Logger;
import com.quna.common.logger.LoggerFactory;

public class CommonsLoggerFactory implements LoggerFactory {
	public Logger create(String type) {
		return new CommonsLogger(LogFactoryImpl.getLog(type));
	}

	public Logger create(Class<?> type) {
		return new CommonsLogger(LogFactoryImpl.getLog(type));
	}

	public Logger create(Object object) {
		return new CommonsLogger(LogFactoryImpl.getLog(object.getClass()));
	}
}
