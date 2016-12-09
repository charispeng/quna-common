package com.quna.common.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.quna.common.cache.Cacheable.CacheType;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CacheEvict {

	Class<?> type();
	/**
	 * String:	整个key都删除
	 * Map 		删除传递field,如不存在field,删除整个map
	 * List 	删除整个Key
	 * @return
	 */
	CacheType cacheType() default CacheType.STRING;
	
	String key();
	
	String field() default "";
}