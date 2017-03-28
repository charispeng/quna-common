package com.quna.common.zookeeper;

import java.util.List;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.AsyncCallback.VoidCallback;
import org.apache.zookeeper.data.Stat;

public interface ZooKeeperOperator {
	/**
	 * 设置ZooKeeper连接
	 * @param connection
	 */
	void setZooKeeperConnection(ZooKeeperConnection connection);
	
	/**
	 * 获取所有子节点
	 * @param path
	 * @return
	 * @throws Exception
	 */
	List<String> getChild(String path) throws Exception;
	
	
	/**
	 * 判断是否存在节点,且获取节点信息
	 * @param path
	 * @return
	 * @throws Exception
	 */
	Stat exists(String path) throws Exception;
	
	/**
	 * 判断节点是否存在并回调
	 * @param path
	 * @param callback
	 * @param args
	 * @throws Exception
	 */
	void exists(String path,StatCallback callback,Object ctx) throws Exception;
	
	/**
	 * 删除节点
	 * @param path
	 * @param version
	 * @return
	 * @throws Exception
	 */
	boolean delete(String path,int version) throws Exception;
	/**
	 * 删除节点并回调
	 * @param path
	 * @param version
	 * @param callback
	 * @param args
	 * @return
	 * @throws Exception
	 */
	boolean delete(String path,int version,VoidCallback callback,Object ctx) throws Exception;
	
	/**
	 * 删除节点
	 * @param path
	 * @return
	 * @throws Exception
	 */
	boolean deleteIgnoreVersion(String path) throws Exception;
}
