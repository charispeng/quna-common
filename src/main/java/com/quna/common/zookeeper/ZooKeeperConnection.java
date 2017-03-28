package com.quna.common.zookeeper;

import org.apache.zookeeper.ZooKeeper;

public interface ZooKeeperConnection {
	/**
	 * 获取Zookeeper对象
	 * @return
	 */
	ZooKeeper getZooKeeper();
	
	/**
	 * 连接zookeeper连接地址
	 */
	void setHosts(String hosts);
	
	/**
	 * 设置timeout
	 * @param timeout
	 */
	void setSessionTimeout(int timeout);
	
	/**
	 * 设置session id
	 * @param id
	 */
	void setSessionId(long id);
	
	/**
	 * 设置密码
	 * @param passwd
	 */
	void setSessionPasswd(byte[] passwd);
	
	/**
	 * 
	 * @param canBeReadOnly
	 */
	void setCanBeReadOnly(boolean canBeReadOnly);
	
	/**
	 * 获取Hosts
	 * @return
	 */
	String getHosts();
	/**
	 * 设置监控回调
	 * @param callback
	 */
	void setWatchedEventCallback(WatchedEventCallback watchedEventCallback);
	/**
	 * 获取监控器
	 * @return
	 */
	WatchedEventCallback getWatchedEventCallback();
	/**
	 * 连接
	 * @param hosts
	 * @param callback
	 */
	void connect();
	
	/**
	 * 关闭zookeeper连接
	 */
	void disconnect();
}
