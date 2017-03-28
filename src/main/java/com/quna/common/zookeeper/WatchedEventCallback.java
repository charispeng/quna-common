package com.quna.common.zookeeper;

import org.apache.zookeeper.WatchedEvent;

public interface WatchedEventCallback {
	/**
	 * 
	 * @param connection
	 */
	void watchedEventCallback(WatchedEvent event,ZooKeeperConnection connection);
	/**
	 * 
	 * @param handler
	 */
	void setWatchedEventHandler(WatchedEventHandler handler);
	/**
	 * 
	 * @return
	 */
	WatchedEventHandler getWatchedEventHandler();
}
