package com.quna.common.zookeeper;

import org.apache.zookeeper.WatchedEvent;

public interface WatchedEventHandler {
	/**
	 * 
	 * @param event
	 */
	void nodeDataChanged(WatchedEvent event,ZooKeeperConnection connection);
	
	/**
	 * 
	 * @param event
	 */
	void none(WatchedEvent event,ZooKeeperConnection connection);
	
	/**
	 * 
	 * @param event
	 */
	void nodeCreated(WatchedEvent event,ZooKeeperConnection connection);
	/**
	 * 
	 * @param event
	 */
	void nodeChildrenChanged(WatchedEvent event,ZooKeeperConnection connection);
	/**
	 * 
	 * @param event
	 */
	void nodeDeleted(WatchedEvent event,ZooKeeperConnection connection);
}
