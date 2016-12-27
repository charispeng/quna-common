package com.quna.common.cache.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public interface JedisPoolFactory {

	JedisPool createJedisPool(String host,int port);
	
	JedisPool createJedisPool(String host,int port,String password);	

	JedisPool createJedisPool(String host,int port,int timeout,String password);
	
	JedisPool createJedisPool(JedisPoolConfig jedisPoolConfig,String host,int port);
	
	JedisPool createJedisPool(JedisPoolConfig jedisPoolConfig,String host,int port,String password);
	
	JedisPool createJedisPool(JedisPoolConfig jedisPoolConfig,String host,int port,int timeout,String password);
}
