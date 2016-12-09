package com.quna.common.cache.redis;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.quna.common.cache.Cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

public class RedisCache implements Cache{
	
	private JedisPool pool;
	
	public RedisCache(JedisPool pool){
		super();
		
		this.pool	= pool;
	}
	
	public Jedis getJedis() {
		Jedis jedis	= pool.getResource();
		return jedis;
	}
	
	@Override
	public byte[] get(byte[] key) {
		Jedis jedis	= getJedis();
		try{
			return jedis.get(key);
		}finally{
			jedis.close();
		}
	}

	@Override
	public List<byte[]> lrange(byte[] key) {
		Jedis jedis	= getJedis();
		try{
			return jedis.lrange(key, 0, jedis.llen(key));
		}finally{
			jedis.close();
		}
	}

	@Override
	public List<byte[]> lrange(byte[] key, int start, int count) {
		Jedis jedis	= getJedis();
		try{
			return jedis.lrange(key, start, count);
		}finally{
			jedis.close();
		}
	}

	@Override
	public byte[] hget(byte[] key,byte[] field) {
		Jedis jedis		= getJedis();
		try{
			return jedis.hget(key, field);
		}finally{
			jedis.close();
		}
	}

	@Override
	public Map<byte[], byte[]> hget(byte[] key) {
		Jedis jedis	= getJedis();
		try{
			return jedis.hgetAll(key);
		}finally{
			jedis.close();
		}
	}
	
	public boolean exists(byte[] key){
		Jedis jedis	= getJedis();
		try{
			return jedis.exists(key);
		}finally{
			jedis.close();
		}
	}
	
	@Override
	public void set(byte[] key,byte[] value) {
		Jedis jedis	= getJedis();
		try{
			jedis.set(key, value);
		}finally{
			jedis.close();
		}
	}
	@Override
	public void set(Map<byte[],byte[]> map) {
		Jedis jedis			= getJedis();
		Transaction tran	= jedis.multi();
		try{
			map.forEach((key,value) ->{
				tran.set(key, value);
			});			
			tran.exec();
		}finally{
			jedis.close();
		}
	}
	
	@Override
	public void lpush(final byte[] key,final List<byte[]> values) {
		Jedis jedis			= getJedis();
		Transaction tran	= jedis.multi();
		try{
			/*
			values.parallelStream().forEach((string)->{
				jedis.lpush(key, string);
			});
			*/
			/*
			values.forEach((string) ->{
				jedis.lpush(key, string);
			});
			*/
			tran.lpush(key,values.toArray(new byte[values.size()][]));
			tran.exec();
		}finally{
			jedis.close();
		}
	}

	@Override
	public void lpush(byte[] key, byte[] value) {
		Jedis jedis	= getJedis();
		try{
			jedis.lpush(key, value);
		}finally{
			jedis.close();
		}
	}

	@Override
	public void hmset(byte[] key, Map<byte[], byte[]> map) {
		Jedis jedis	= getJedis();
		try{
			jedis.hmset(key, map);
		}finally{
			jedis.close();
		}
	}

	@Override
	public void hset(byte[] key, byte[] field, byte[] value) {
		Jedis jedis	= getJedis();
		try{
			jedis.hset(key, field, value);
		}finally{
			jedis.close();
		}
	}
	@Override
	public void expire(byte[] key,int seconds) {
		Jedis jedis	= getJedis();
		try{
			jedis.expire(key, seconds);
		}finally{
			jedis.close();
		}
	
	}

	@Override
	public void expireAt(byte[] key,long unixTime) {
		Jedis jedis	= getJedis();
		try{
			jedis.expireAt(key, unixTime);
		}finally{
			jedis.close();
		}
	}

	@Override
	public long ttl(byte[] key) {
		Jedis jedis	= getJedis();
		try{
			return jedis.ttl(key);
		}finally{
			jedis.close();
		}
	}
	
	
	@SuppressWarnings("unused")
	private Object eval(byte[] script){
		Jedis jedis	= getJedis();
		try{
			Object object	= jedis.eval(script);
			return object;
		}finally{
			jedis.close();
		}
	}
	
	private Object eval(byte[] script,int keyCount,byte[][] params){
		Jedis jedis	= getJedis();
		try{
			Object object	= jedis.eval(script, keyCount, params);
			return object;
		}finally{
			jedis.close();
		}
	}

	@Override
	public void set(int expire, long expireAt, byte[] key, byte[] value) {
		Jedis jedis			= getJedis();
		Transaction tran	= jedis.multi();
		try{
			if(expire > 0){
				tran.setex(key, expire, value);
			}else if(expireAt > 0){
				tran.set(key, value);
				tran.expireAt(key, expireAt);
			}else{
				tran.set(key, value);
			}
			tran.exec();
		}finally{
			jedis.close();
		}
	}

	@Override
	public byte[] lpop(byte[] key) {
		Jedis jedis	= getJedis();
		try{
			return jedis.lpop(key);
		}finally{
			jedis.close();
		}
	}

	@Override
	public byte[] rpop(byte[] key) {
		Jedis jedis	= getJedis();
		try{
			return jedis.rpop(key);
		}finally{
			jedis.close();
		}
	}

	@Override
	public void rpush(byte[] key, byte[] value) {
		Jedis jedis	= getJedis();
		try{
			jedis.rpush(key,value);
		}finally{
			jedis.close();
		}
	}

	@Override
	public void lpush(int expire, long expireAt, byte[] key, List<byte[]> values) {
		boolean exists	= exists(key);
		if(exists){
			lpush(key, values);
		}else{
			if(expire > 0){
				StringBuilder scriptSB	= new StringBuilder("if redis.call('lpush',KEYS[1]");
				for(int i=2,j=0; j<values.size();j++){
					scriptSB.append(",ARGV["+i+"]");					
					i++;
				}
				scriptSB.append(") then return redis.call('expire',KEYS[1],ARGV[1]) else return -1 end");
				byte[][] params	= new byte[][]{key,String.valueOf(expire).getBytes()};
				params			= Arrays.copyOf(params, params.length + values.size());
				byte[][] src	= values.toArray(new byte[values.size()][]);				
				System.arraycopy(src, 0, params, 2, src.length);
				eval(scriptSB.toString().getBytes(), 1,params);
			}else if(expireAt > 0){
				StringBuilder scriptSB	= new StringBuilder("if redis.call('lpush',KEYS[1]");
				for(int i=2,j=0; j<values.size();j++){
					scriptSB.append(",ARGV["+i+"]");					
					i++;
				}
				scriptSB.append(") then return redis.call('expireAt',KEYS[1],ARGV[1]) else return -1 end");
				
				byte[][] params	= new byte[][]{key,String.valueOf(expireAt).getBytes()};
				params			= Arrays.copyOf(params, params.length + values.size());
				byte[][] src	= values.toArray(new byte[values.size()][]);
				System.arraycopy(src, 0, params, 2, src.length);				
				eval(scriptSB.toString().getBytes(), 1,params);
			}else{
				lpush(key, values);
			}
		}
	}

	@Override
	public void lpush(int expire, long expireAt, byte[] key, byte[] value) {
		boolean exists	= exists(key);
		if(exists){
			lpush(key, value);
		}else{
			if(expire > 0){
				StringBuilder scriptSB	= new StringBuilder("if redis.call('lpush',KEYS[1],ARGV[2]) then return redis.call('expire',KEYS[1],ARGV[1]) else return -1 end");
				byte[][] params	= new byte[][]{key,String.valueOf(expire).getBytes(),value};
				eval(scriptSB.toString().getBytes(),1,params);
			}else if(expireAt > 0){
				StringBuilder scriptSB	= new StringBuilder("if redis.call('lpush',KEYS[1],ARGV[2]) then return redis.call('expireAt',KEYS[1],ARGV[1]) else return -1 end");
				byte[][] params	= new byte[][]{key,String.valueOf(expireAt).getBytes(),value};
				eval(scriptSB.toString().getBytes(),1,params);
			}else{
				lpush(key, value);
			}
		}
	}

	@Override
	public void rpush(int expire, long expireAt, byte[] key, byte[] value) {
		boolean exists	= exists(key);
		if(exists){
			rpush(key, value);
		}else{
			if(expire > 0){
				StringBuilder scriptSB	= new StringBuilder("if redis.call('rpush',KEYS[1],ARGV[2]) then return redis.call('expire',KEYS[1],ARGV[1]) else return -1 end");
				byte[][] params	= new byte[][]{key,String.valueOf(expire).getBytes(),value};
				eval(scriptSB.toString().getBytes(),1,params);
			}else if(expireAt > 0){
				StringBuilder scriptSB	= new StringBuilder("if redis.call('rpush',KEYS[1],ARGV[2]) then return redis.call('expireAt',KEYS[1],ARGV[1]) else return -1 end");
				byte[][] params	= new byte[][]{key,String.valueOf(expireAt).getBytes(),value};
				eval(scriptSB.toString().getBytes(),1,params);
			}else{
				rpush(key, value);
			}
		}		
	}

	@Override
	public void hmset(int expire, long expireAt, byte[] key, Map<byte[], byte[]> map) {
		boolean exists	= exists(key);
		if(exists){
			hmset(key,map);
		}else{
			if(expire > 0){
				StringBuilder scriptSB	= new StringBuilder("if redis.call('HMSET',KEYS[1]");
				for(int i=2,j=0; j<map.size();j++){
					scriptSB.append(",ARGV["+i+"],ARGV["+(i+1)+"]");	
					i+=2;
				}
				scriptSB.append(") then return redis.call('expire',KEYS[1],ARGV[1]) else return -1 end");
				byte[][] params	= new byte[][]{key,String.valueOf(expire).getBytes()};
				params			= Arrays.copyOf(params, params.length + (map.size() * 2));
				byte[][] src	= new byte[(map.size() * 2)][];
				int i			= 0;
				for(Entry<byte[],byte[]> entry : map.entrySet()){
					src[i]		= entry.getKey();
					src[i+1]	= entry.getValue();
					i+=2;
				}
				System.arraycopy(src, 0, params, 2, src.length);
				eval(scriptSB.toString().getBytes(), 1,params);
			}else if(expireAt > 0){
				StringBuilder scriptSB	= new StringBuilder("if redis.call('HMSET',KEYS[1]");
				for(int i=2,j=0; j<map.size();j++){
					scriptSB.append(",ARGV["+i+"],ARGV["+(i+1)+"]");	
					i+=2;
				}
				scriptSB.append(") then return redis.call('expireAt',KEYS[1],ARGV[1]) else return -1 end");
				byte[][] params	= new byte[][]{key,String.valueOf(expireAt).getBytes()};
				params			= Arrays.copyOf(params, params.length + (map.size() * 2));
				byte[][] src	= new byte[(map.size() * 2)][];
				int i			= 0;
				for(Entry<byte[],byte[]> entry : map.entrySet()){
					src[i]		= entry.getKey();
					src[i+1]	= entry.getValue();
					i+=2;
				}
				System.arraycopy(src, 0, params, 2, src.length);
				eval(scriptSB.toString().getBytes(), 1,params);
			}else{
				hmset(key,map);
			}
		}
	}

	@Override
	public void hset(int expire, long expireAt, byte[] key, byte[] field, byte[] value) {
		boolean exists	= exists(key);
		if(exists){
			hset(key,field,value);
		}else{
			if(expire > 0){
				StringBuilder scriptSB	= new StringBuilder("if redis.call('HSET',KEYS[1],ARGV[2],ARGV[3]) then return redis.call('expire',KEYS[1],ARGV[1]) else return -1 end");
				byte[][] params	= new byte[][]{key,String.valueOf(expire).getBytes(),field,value};
				eval(scriptSB.toString().getBytes(), 1,params);
			}else if(expireAt > 0){
				StringBuilder scriptSB	= new StringBuilder("if redis.call('HSET',KEYS[1],ARGV[2],ARGV[3]) then return redis.call('expireAt',KEYS[1],ARGV[1]) else return -1 end");
				byte[][] params	= new byte[][]{key,String.valueOf(expireAt).getBytes(),field,value};
				eval(scriptSB.toString().getBytes(), 1,params);
			}else{
				hset(key,field,value);
			}
		}
	}
	
}