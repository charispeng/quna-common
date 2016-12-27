package com.quna.common.logger.jdk;

import java.util.logging.Level;

import com.quna.common.logger.Logger;
/**
 * 只支持Info和以上日志 *
 * @author 252054576@qq.com
 * @date 2016年12月27日  下午4:50:51
 */
public class JdkLogger implements Logger{

	private java.util.logging.Logger logger	= null;
	//private Level level						= null;
	
	public JdkLogger(java.util.logging.Logger logger){
		this.logger	= logger;
	//	level		= logger.getLevel();
	}
	
	@Override
	public boolean isDebugEnabled() {		
		return logger.isLoggable(Level.CONFIG);
	}

	@Override
	public boolean isInfoEnabled() {
		//return level.equals(Level.INFO) || level.equals(Level.WARNING) || level.equals(Level.SEVERE) || level.equals(Level.ALL);
		return logger.isLoggable(Level.INFO);
	}

	@Override
	public boolean isWarnEnabled() {
		//return level.equals(Level.WARNING) || level.equals(Level.SEVERE) || level.equals(Level.ALL);
		return logger.isLoggable(Level.WARNING);
	}

	@Override
	public boolean isErrorEnabled() {
		//return level.equals(Level.SEVERE) || level.equals(Level.ALL);
		return logger.isLoggable(Level.SEVERE);
	}

	@Override
	public boolean isFatalEnabled() {
		//return level.equals(Level.SEVERE) || level.equals(Level.ALL);
		return logger.isLoggable(Level.SEVERE);
	}

	@Override
	public void debug(String message) {
		logger.config(message);
	}

	@Override
	public void debug(Throwable exception) {
		logger.config(exception.toString());		
	}

	@Override
	public void debug(String message, Throwable exception) {
		logger.config(message + " " + exception);
	}

	@Override
	public void debug(String format, Object... args) {
		logger.config(String.format(format, args));
	}

	@Override
	public void info(String message) {
		logger.info(message);
	}

	@Override
	public void info(Throwable exception) {
		logger.info(exception.toString());
	}

	@Override
	public void info(String message, Throwable exception) {
		logger.info(message + " " + exception);
	}

	@Override
	public void info(String format, Object... args) {
		logger.info(String.format(format, args));		
	}

	@Override
	public void warn(String message) {
		logger.warning(message);
	}

	@Override
	public void warn(Throwable exception) {
		logger.warning(exception.toString());
	}

	@Override
	public void warn(String message, Throwable exception) {
		logger.warning(message + " " + exception);
	}

	@Override
	public void warn(String format, Object... args) {
		logger.warning(String.format(format, args));		
	}

	@Override
	public void error(String message) {
		logger.severe(message);
	}

	@Override
	public void error(Throwable exception) {
		logger.severe(exception.toString());
		exception.printStackTrace();
	}

	@Override
	public void error(String message, Throwable exception) {
		logger.severe(message + " " + exception);
		exception.printStackTrace();
	}

	@Override
	public void error(String format, Object... args) {
		logger.severe(String.format(format, args));
	}

	@Override
	public void fatal(String message) {
		logger.severe(message);
	}

	@Override
	public void fatal(Throwable exception) {
		logger.severe(exception.toString());
		exception.printStackTrace();
	}

	@Override
	public void fatal(String message, Throwable exception) {
		logger.severe(message + " " + exception);
		exception.printStackTrace();
	}

	@Override
	public void fatal(String format, Object... args) {
		logger.severe(String.format(format, args));
	}

}
