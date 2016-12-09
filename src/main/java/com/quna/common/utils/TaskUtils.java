package com.quna.common.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * <pre>
 * <b>任务管理</b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2014-1-1 上午10:00:01
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2014-01-01 10:00:01     252054576@qq.com
 *         new file.
 * </pre>
 */
public class TaskUtils {
	/**
	 * 任务最大条数
	 */
	private static final int COREPOOLSIZE 				= 10;

	/**
     * 线程池，用来管理所有的任务
     */
	public static final ScheduledExecutorService ses 	= Executors.newScheduledThreadPool(COREPOOLSIZE);

	/**
	 * 执行完第一次后再执行
	 * 
	 * @param runnable
	 *            任务对象
	 * @param initialDelay
	 *            多少秒后执行
	 * @param delay
	 *            间隔多少秒
	 */
	public static void runWithFixed(Runnable runnable, int initialDelay,int delay) {
		ses.scheduleWithFixedDelay(runnable, initialDelay, delay,TimeUnit.SECONDS);
	}

	/**
	 * 执行完第一次后再执行
	 * 
	 * @param runnable
	 *            任务对象
	 * @param initialDelay
	 *            多少时间后执行
	 * @param delay
	 *            间隔多少
	 * @param TimeUnit
	 *            时间单位
	 */
	public static void runWithFixed(Runnable runnable, int initialDelay,
			int delay, TimeUnit unit) {
		ses.scheduleWithFixedDelay(runnable, initialDelay, delay, unit);
	}

	/**
	 * 不管第一次有没有执行完，第二次都会按时执行
	 * 
	 * @param runnable
	 *            任务对象
	 * @param initialDelay
	 *            多少秒后执行
	 * @param delay
	 *            间隔多少秒
	 */
	public static void runAtFixed(Runnable runnable, int initialDelay, int delay) {
		ses.scheduleAtFixedRate(runnable, initialDelay, delay, TimeUnit.SECONDS);
	}

	/**
	 * 
	 * 不管第一次有没有执行完，第二次都会按时执行
	 * 
	 * @param runnable
	 *            任务对象
	 * @param initialDelay
	 *            多少时间后执行
	 * @param delay
	 *            间隔多少
	 * @param TimeUnit
	 *            时间单位
	 */
	public static void runAtFixed(Runnable runnable, int initialDelay,
			int delay, TimeUnit unit) {
		ses.scheduleAtFixedRate(runnable, initialDelay, delay, unit);
	}
}
