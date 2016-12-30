package com.quna.common.logger.console;

import com.quna.common.logger.Logger;
import com.quna.common.logger.LoggerFactory;

public class DefaultLoggerFactory implements LoggerFactory {
	private static LoggerFactory _default;

	static {
		setDefault(false, true, true, true, true);
	}

	public static LoggerFactory getDefault() {
		return _default;
	}

	public static synchronized void setDefault(boolean isDebugEnabled,
			boolean isInfoEnabled,
			boolean isWarnEnabled,
			boolean isErrorEnabled,
			boolean isFatalEnabled) {
		_default = new DefaultLoggerFactory(isDebugEnabled,
				isInfoEnabled,
				isWarnEnabled,
				isErrorEnabled,
				isFatalEnabled);
	}

	private boolean isDebugEnabled;
	private boolean isInfoEnabled;
	private boolean isWarnEnabled;
	private boolean isErrorEnabled;
	private boolean isFatalEnabled;

	public DefaultLoggerFactory() {
		this(false, true, true, true, true);
	}

	public DefaultLoggerFactory(boolean isDebugEnabled,
			boolean isInfoEnabled,
			boolean isWarnEnabled,
			boolean isErrorEnabled,
			boolean isFatalEnabled) {
		this.isDebugEnabled = isDebugEnabled;
		this.isInfoEnabled = isInfoEnabled;
		this.isWarnEnabled = isWarnEnabled;
		this.isErrorEnabled = isErrorEnabled;
		this.isFatalEnabled = isFatalEnabled;
	}

	public Logger create(String type) {
		return new DefaultLogger(type,
				this.isDebugEnabled,
				this.isInfoEnabled,
				this.isWarnEnabled,
				this.isErrorEnabled,
				this.isFatalEnabled);
	}

	public Logger create(Class<?> type) {
		return create(type.getSimpleName());
	}

	public Logger create(Object object) {
		/*
		return new DefaultLogger(object.getClass().getSimpleName(),
				this.isDebugEnabled,
				this.isInfoEnabled,
				this.isWarnEnabled,
				this.isErrorEnabled,
				this.isFatalEnabled);
		*/
		
		return create(object.getClass().getSimpleName());
	}

}
