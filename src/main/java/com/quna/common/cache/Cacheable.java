package com.quna.common.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
@Documented
@Inherited
public @interface Cacheable {

	public enum CacheType{STRING,MAP,LIST};
	
	Class<?> type() default Object.class;
	/**
	 * 缓存类型,String,Map,List
	 * @return
	 */
	CacheType cacheType() default CacheType.STRING;
	
	/**
	 * 设置Key值,支持自定义的表达式,不支持spring表达式
	 * @return
	 */
	String key();
	
	/**
	 * 设置Field值,支持自定义的表达式,不支持spring表达式
	 * @return
	 */
	String field() default "";	
	/**
	 * 设置指定时间过期,可用使用表达式,
	 * 表达式结果 2015-05-08 / 20150508 / Date 
	 * 如果设置了expire,此设置无效
	 * @return
	 */
	String expireAt() default "0";
	/**
	 * 设置过期时间,首选
	 * @return
	 */
	int expire() default 0;
}
