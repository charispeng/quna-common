package com.quna.common.zookeeper;

import org.apache.zookeeper.WatchedEvent;

import com.quna.common.logger.LogUtil;
import com.quna.common.logger.Logger;

public class BasicWatchedEventHandler implements WatchedEventHandler {
	
	private static Logger logger	= LogUtil.getLoggerFactory(BasicWatchedEventHandler.class).create(BasicWatchedEventHandler.class);

	public void nodeDataChanged(WatchedEvent event, ZooKeeperConnection connection) {
		if(logger.isInfoEnabled()){
			logger.info("nodeDataChanged,WatchedEvent:" + event + " connection:" + connection);
		}
	}

	public void none(WatchedEvent event, ZooKeeperConnection connection) {
		if(logger.isInfoEnabled()){
			logger.info("none,WatchedEvent:" + event + " connection:" + connection);
		}
	}

	public void nodeCreated(WatchedEvent event, ZooKeeperConnection connection) {
		if(logger.isInfoEnabled()){
			logger.info("nodeCreated,WatchedEvent:" + event + " connection:" + connection);
		}
	}

	public void nodeChildrenChanged(WatchedEvent event, ZooKeeperConnection connection) {
		if(logger.isInfoEnabled()){
			logger.info("nodeChildrenChanged,WatchedEvent:" + event + " connection:" + connection);	
		}
	}

	public void nodeDeleted(WatchedEvent event, ZooKeeperConnection connection) {
		if(logger.isInfoEnabled()){
			logger.info("nodeDeleted,WatchedEvent:" + event + " connection:" + connection);
		}
	}
}
