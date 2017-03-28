package com.quna.common.zookeeper;

import org.apache.zookeeper.CreateMode;

public interface ZooKeeperByteOperator extends ZooKeeperOperator {
	/**
	 * 创建节点
	 * @param path
	 * @param data
	 * @param createMode
	 * @return
	 * @throws Exception
	 */
	boolean create(String path,byte[] data,CreateMode createMode) throws Exception;
	/**
	 * 获取节点数据
	 * @param path
	 * @return
	 * @throws Exception
	 */
	byte[] getData(String path) throws Exception;	
	/**
	 * 设置节点数据,当节点不存在时,会自动创建新节点
	 * @param path
	 * @param data
	 * @param createMode
	 * @return
	 * @throws Exception
	 */
	boolean setData(String path,byte[] data,int version,CreateMode createMode) throws Exception;
	/**
	 * 忽略版本设置节点数据,当节点不存在时,会自动创建新节点
	 * 不可以多人同时修改单一节点数据
	 * @param path
	 * @param data
	 * @param createMode
	 * @return
	 * @throws Exception
	 */
	boolean setDataIgnoreVersion(String path,byte[] data,CreateMode createMode) throws Exception;
	
	/**
	 * 当节点数据不存在时创建新节点
	 * 当节点数据存在时判断节点数据是否相同,如果相同不保存节点,不同更新节点
	 * 
	 * @param path
	 * @param data
	 * @param createMode
	 * @return
	 * @throws Exception
	 */
	boolean setDataNotEqual(String path,byte[] data,int version,CreateMode createMode) throws Exception;
	
	/**
	 * 当节点数据不存在时创建新节点
	 * 当节点数据存在时判断节点数据是否相同,如果相同不保存节点,不同更新节点
	 * 不可以多人同时修改单一节点数据
	 * 
	 * @param path
	 * @param data
	 * @param createMode
	 * @return
	 * @throws Exception
	 */
	boolean setDataNotEqualIgnoreVersion(String path,byte[] data,CreateMode createMode) throws Exception;
}
