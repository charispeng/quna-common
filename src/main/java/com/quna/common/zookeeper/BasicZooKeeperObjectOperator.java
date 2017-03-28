package com.quna.common.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;

import com.quna.common.serialize.Serialization;

public class BasicZooKeeperObjectOperator<T> extends BasicZooKeeperOperator implements ZooKeeperObjectOperator<T>{
	
	private Serialization serialization;	
	
	public BasicZooKeeperObjectOperator(){}
	
	public BasicZooKeeperObjectOperator(ZooKeeperConnection connection){
		this();
		this.connection		= connection;
	}
	public BasicZooKeeperObjectOperator(ZooKeeperConnection connection,Serialization serialization){
		this(connection);
		this.serialization	= serialization;
	}
	
	public void setSerialization(Serialization serialization){
		this.serialization	= serialization;
	}
	
	public boolean create(String path, Object object, CreateMode createMode) throws Exception {
		String[] paths		= path.split("\\/");
		String createPath	= "";
		for(String tmpPath : paths){
			if(!StringUtils.hasText(tmpPath)){continue;}			
			createPath		+="/" + tmpPath;		
			Stat stat		= this.connection.getZooKeeper().exists(createPath, true);
			if(null != stat){
				continue;
			}
			this.connection.getZooKeeper().create(createPath, serialization.serialize(object), Ids.OPEN_ACL_UNSAFE, createMode);
		}
		return true;
	}	

	public T getData(String path,Class<T> clazz) throws Exception {
		if(null != exists(path)){
			byte[] data	= this.connection.getZooKeeper().getData(path,true,null);
			return serialization.deserialize(data,clazz);
		}
		return null;
	}

	public boolean setData(String path, Object object, int version, CreateMode createMode) throws Exception {
		Stat stat		= exists(path);
		if(null == stat){
			return create(path, object, createMode);
		}else{
			stat		= this.connection.getZooKeeper().setData(path, serialization.serialize(object), version);
			return null != stat ? true : false;
		}
	}
	
	public boolean setDataIgnoreVersion(String path, Object object, CreateMode createMode) throws Exception {
		Stat stat		= exists(path);
		if(null == stat){
			return create(path, object, createMode);
		}else{
			stat		= this.connection.getZooKeeper().setData(path, serialization.serialize(object), stat.getVersion());
			return null != stat ? true : false;
		}
	}

	public boolean setDataNotEqual(String path, Object object,Class<T> clazz,int version, CreateMode createMode) throws Exception {
		Stat stat				= exists(path);
		if(null == stat){
			return create(path, object, createMode);
		}else{
			Object tmpObject	= getData(path,clazz);
			if(!equal(object, tmpObject)){
				stat	= this.connection.getZooKeeper().setData(path, serialization.serialize(object), version);
				return null != stat ? true : false;
			}
		}
		return true;
	}
	
	public boolean setDataNotEqualIgnoreVersion(String path, Object object,Class<T> clazz,CreateMode createMode) throws Exception {
		Stat stat			= exists(path);
		if(null == stat){
			return create(path, object, createMode);
		}else{
			Object tmpObject= getData(path,clazz);
			if(!equal(object, tmpObject)){
				stat	= this.connection.getZooKeeper().setData(path, serialization.serialize(object), stat.getVersion());
				return null != stat ? true : false;
			}
		}
		return true;
	}
	
	private boolean equal(Object object,Object otherObject){
		if(null == object || otherObject == null) return false;
		return object.equals(otherObject);
	}
}
