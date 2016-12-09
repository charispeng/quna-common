package com.quna.common.cache;

import java.util.List;
import java.util.Map;

public interface Cache{
	
	//==========================
	byte[] get(byte[] key);
	
	List<byte[]> lrange(byte[] key);
	
	List<byte[]> lrange(byte[] key,int start,int count);
	
	byte[] lpop(byte[] key);
	
	byte[] rpop(byte[] key);
	
	byte[] hget(byte[] key,byte[] field);
	
	Map<byte[],byte[]> hget(byte[] key);
	
	//===============================
	
	
	void set(byte[] key,byte[] value);
	
	void set(Map<byte[],byte[]> map);
	//有效期
	void set(int expire,long expireAt,byte[] key,byte[] value);
	
	void lpush(byte[] key,List<byte[]> values);
	
	void lpush(byte[] key,byte[] value);
	
	void rpush(byte[] key,byte[] value);
	
	void lpush(int expire,long expireAt,byte[] key,List<byte[]> values);
		
	void lpush(int expire,long expireAt,byte[] key,byte[] value);
		
	void rpush(int expire,long expireAt,byte[] key,byte[] value);
	
	void hmset(byte[] key,Map<byte[],byte[]> map);
	
	void hset(byte[] key,byte[] field,byte[] value);
	
	void hmset(int expire,long expireAt,byte[] key,Map<byte[],byte[]> map);
	
	void hset(int expire,long expireAt,byte[] key,byte[] field,byte[] value);
	
	boolean exists(byte[] key);
	
	void expire(byte[] key,int seconds);
	
	void expireAt(byte[] key,long unixTime);
	
	long ttl(byte[] key);
}
