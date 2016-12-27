package com.quna.common.cache.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class DefaultJedisPoolFactory implements JedisPoolFactory {

	private static final int DEFAULT_TIMEOUT		= 2000;
	
	public static DefaultJedisPoolFactory INSTANCE	= new DefaultJedisPoolFactory();

	//========================
	@Override
	public JedisPool createJedisPool(String host, int port) {
		return createJedisPool(host, port,null);
	}

	@Override
	public JedisPool createJedisPool(String host, int port, String password) {
		return createJedisPool(host, port, DEFAULT_TIMEOUT, password);
	}

	@Override
	public JedisPool createJedisPool(String host, int port, int timeout, String password) {
		return createJedisPool(new JedisPoolConfigBuilder().build(), host, port, password);
	}

	@Override
	public JedisPool createJedisPool(JedisPoolConfig jedisPoolConfig,String host, int port) {
		return createJedisPool(jedisPoolConfig, host, port,null);
	}
	
	@Override
	public JedisPool createJedisPool(JedisPoolConfig jedisPoolConfig,String host, int port, String password) {
		return createJedisPool(jedisPoolConfig, host, port, DEFAULT_TIMEOUT, password);
	}

	@Override
	public JedisPool createJedisPool(JedisPoolConfig jedisPoolConfig,String host, int port, int timeout, String password) {
		return new JedisPool(jedisPoolConfig, host, port, timeout, password);
	}
	
	public static class JedisPoolConfigBuilder{
		private int maxTotal						= 50;	//8
		private int maxIdle							= 50;	//8
		private int maxWait							= 1000;
		private int minIdle							= 0;
		private boolean testOnBorrow				= true;
		private boolean testOnReturn				= true;
		private boolean testWhileIdle				= true;
		private int minEvictableIdleTimeMillis		= 60000;
		private int timeBetweenEvictionRunsMillis	= 30000;
		private int numTestsPerEvictionRun			= -1;
		
		public JedisPoolConfigBuilder setMaxTotal(int maxTotal) {
			this.maxTotal = maxTotal;
			return this;
		}
		public JedisPoolConfigBuilder setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
			return this;
		}
		public JedisPoolConfigBuilder setMaxWait(int maxWait) {
			this.maxWait = maxWait;
			return this;
		}
		public JedisPoolConfigBuilder setMinIdle(int minIdle) {
			this.minIdle = minIdle;
			return this;
		}
		public JedisPoolConfigBuilder setTestOnBorrow(boolean testOnBorrow) {
			this.testOnBorrow = testOnBorrow;
			return this;
		}
		public JedisPoolConfigBuilder setTestOnReturn(boolean testOnReturn) {
			this.testOnReturn = testOnReturn;
			return this;
		}
		public JedisPoolConfigBuilder setTestWhileIdle(boolean testWhileIdle) {
			this.testWhileIdle = testWhileIdle;
			return this;
		}
		public JedisPoolConfigBuilder setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
			this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
			return this;
		}
		public JedisPoolConfigBuilder setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
			this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
			return this;
		}
		public JedisPoolConfigBuilder setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
			this.numTestsPerEvictionRun = numTestsPerEvictionRun;
			return this;
		}
		
		public JedisPoolConfig build(){
			JedisPoolConfig jedisPoolConfig	= new JedisPoolConfig();
			jedisPoolConfig.setMaxTotal(maxTotal);
			jedisPoolConfig.setMaxIdle(maxIdle);
			jedisPoolConfig.setMaxWaitMillis(maxWait);
			jedisPoolConfig.setMinIdle(minIdle);
			jedisPoolConfig.setTestOnBorrow(testOnBorrow);
			jedisPoolConfig.setTestOnReturn(testOnReturn);
			jedisPoolConfig.setTestWhileIdle(testWhileIdle);
			jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
			jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
			jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
			return jedisPoolConfig;
		}
	}
}