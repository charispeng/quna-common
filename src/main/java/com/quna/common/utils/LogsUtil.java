package com.quna.common.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
/** 
 * <pre>
 * <b>日志</b>
 * <b>Description:</b> 
 * 
 * <b>Author:</b> 252054576@qq.com
 * <b>Date:</b> 2015年9月18日 上午10:58:30
 * <b>Copyright:</b> Copyright &copy;2006-2015 quna.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2015年9月18日 上午10:58:30    252054576@qq.com  new file.
 * </pre>
 */
public abstract class LogsUtil extends _Util {

    // http://blog.csdn.net/yycdaizi/article/details/8276265
    // http://hi.baidu.com/wuyunchong/item/64ef794815f766afde2a9f23
    // http://www.blogjava.net/justfly/archive/2009/02/27/25495.html
    // http://thoughtfly.iteye.com/blog/1436529

    /**
     * Key-Value显示时, Key排版的宽度.
     */
    public static final int NAME_LENGTH = 14;

    /**
     * 日志信息分隔线.
     */
    public static final String LINE = "|-----------------------------------------------------------------------------------------------------------";

    /**
     * 模块日志标记.
     */
    public static final String PREFIX1 = "| ";

    /**
     * 功能业务日志标记.
     */
    public static final String PREFIX2 = "| @ ";
    public static final String PREFIX3 = "|   ";
    public static final String PREFIX4 = "|     ";
    public static final String PREFIX5 = "|       ";

    /**
     * 多线程并发控制锁.
     */
    private static final Object _LOCK 			= new Object();

    /**
     * 将日志文件分别输出到控制台和相应的日志文件.
     */
    private static final boolean ADDITIVITY		= false;

    /**
     * Log4j 默认的日志格式布局: [%d{yyyy-MM-dd HH:mm:ss.SSS}][%5p][%6c] %m%n
     */
    public static final String LOGGER_LAYOUT 	= "[%d{yyyy-MM-dd HH:mm:ss.SSS}][%5p][%l] %m%n";

    /**
     * 程序中所有待使用的日志记录器容器.
     */
    private static Map<String, Logger> loggers 	= new ConcurrentHashMap<String, Logger>();

    /**
     * 日志记录器.
     */
    private static final Logger logger			 = Logger.getRootLogger();
    
    /**
     * 根据日志记录器名称获取记录器.<br/>
     * 如果配置不存在, 则采用默认配置.
     * 默认日志输出等级为INFO
     * 默认输出格式为"[%d{yyyy-MM-dd HH:mm:ss.SSS}][%5p][%l] %m%n"
     * 
     * 
     * @param loggerName 日志记录器名称.
     * @return Logger
     */
    public static Logger getLogger(String loggerName) {
        // 采用默认配置.
        return getLogger(loggerName,Level.INFO, LOGGER_LAYOUT, true);
    }

    /**
     * 根据日志记录器名称获取记录器.<br/>
     * 如果配置不存在, 则采用默认配置.
     * 
     * @param loggerName 日志记录器名称.
     * @param level
     * @param layout
     * @param consoleEnable
     * @return
     */
    public static Logger getLogger(String loggerName, Level level, String layout, boolean consoleEnable) {
        String _fileName = FileUtils.getProjectPath() + "../../logs/" + loggerName + ".log";
        return getLogger(loggerName, level, layout, _fileName, consoleEnable);
    }
    
    /**
    * 根据日志记录器名称获取记录器.<br/>
     * 如果配置不存在, 则采用默认配置.
     * 
     * @param loggerName
     * @param level
     * @param layout
     * @param filePath			保存地址
     * @param consoleEnable
     * @return
     */
    public static Logger getLogger(String loggerName, Level level, String layout,String filePath,boolean consoleEnable) {
    	if (!loggers.containsKey(loggerName)) {
            synchronized (_LOCK) {
                if (!loggers.containsKey(loggerName)) {
                    Logger _logger = Logger.getLogger(loggerName);
                    _logger.setAdditivity(ADDITIVITY);
                    
                    /**
                     * 设置日志等级
                     */
                    _logger.setLevel(level);

                    PatternLayout _layout = new PatternLayout();
                    _layout.setConversionPattern(layout);

                    Appender fileAppender = null;
                    try {
                        fileAppender = new DailyRollingFileAppender(_layout, filePath, "yyyy-MM-dd");
                        // new RollingFileAppender(_layout, _fileName, true);
                        // fileAppendar.setMaximumFileSize(10 * 1024 * 1024); //
                        // 每个文件10KB.
                        // fileAppendar.setMaxBackupIndex(1024); // 可保留1024个文件.
                    } catch (IOException e) {
                        logger.error("bulid " + loggerName + "logger failed, invalid log fileName:" + filePath, e);
                    }
                    _logger.addAppender(fileAppender);

                    if (consoleEnable) {
                        Appender consoleAppender = new ConsoleAppender(_layout, "System.out");
                        consoleAppender.setLayout(_layout);
                        _logger.addAppender(consoleAppender);
                    }

                    loggers.put(loggerName, _logger);
                }
            }
        }

        return loggers.get(loggerName);
    }

    /**
     * 输出字符串（String）的调试信息.
     * 
     * @param name 		属性名称.
     * @param source 	对应参数实例.
     * @return String 	字符串形式的信息.
     */
    public static String debugInfo(String name, Object source) {
        return name + "= " + ObjectUtils.toString(source, "<null>");
    }

    /**
     * 输出字符串（String）的调试信息.
     * 
     * @param name 			属性名称.
     * @param source 		对应参数实例.
     * @param nameLength 	属性名称自动补充的长度.
     * @return String 		字符串形式的信息.
     */
    public static String debugInfo(String name, Object source, int nameLength) {
        return StringUtils.keepLen(name, nameLength, " ", false) + "= " + ObjectUtils.toString(source, "<null>");
    }

    /**
     * 输出集合（Collection）的调试信息.
     * 
     * @param name 		属性名称.
     * @param coll 		对应的集合实例.
     * @return String	 字符串形式的调试信息.
     */
    public static String debugInfo(String name, Collection<?> coll) {
        return name + "= {" + StringUtils.toDelimitedString(coll, ",") + "}";
    }

    /**
     * 输出集合（Collection）的调试信息.
     * 
     * @param name 			属性名称.
     * @param coll 			对应的集合实例.
     * @param nameLength 	属性名称自动补充的长度.
     * @return String 		字符串形式的调试信息.
     */
    public static String debugInfo(String name, Collection<?> coll, int nameLength) {
        return StringUtils.keepLen(name, nameLength, " ", false) + "= {" + StringUtils.toDelimitedString(coll, ",") + "}";
    }

}
