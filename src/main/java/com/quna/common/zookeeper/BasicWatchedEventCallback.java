package com.quna.common.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.quna.common.logger.LogUtil;
import com.quna.common.logger.Logger;

public class BasicWatchedEventCallback implements WatchedEventCallback {
	private static Logger logger	= LogUtil.getLoggerFactory(BasicWatchedEventCallback.class).create(BasicWatchedEventCallback.class);
	private WatchedEventHandler handler;
	
	public BasicWatchedEventCallback(){}
	
	public BasicWatchedEventCallback(WatchedEventHandler handler){
		this.handler				= handler;
	}
	public void setWatchedEventHandler(WatchedEventHandler handler) {
		this.handler				= handler;		
	}
	public WatchedEventHandler getWatchedEventHandler() {
		return handler;
	}
	public void watchedEventCallback(WatchedEvent event,ZooKeeperConnection connection) {
		if(logger.isDebugEnabled()){
			logger.debug(event.toString());
		}
		if(event.getState() == KeeperState.SyncConnected){
			if(event.getType() == EventType.NodeDataChanged){
				handler.nodeDataChanged(event,connection);
			}else if(event.getType() == EventType.None){
				handler.none(event,connection);
			}else if(event.getType() == EventType.NodeCreated){
				handler.nodeCreated(event,connection);
			}else if(event.getType() == EventType.NodeChildrenChanged){
				handler.nodeChildrenChanged(event,connection);
			}else if(event.getType() == EventType.NodeDeleted){
				handler.nodeDeleted(event,connection);
			}
		}else if(event.getState() == KeeperState.Expired || event.getState() == KeeperState.Disconnected){
			logger.warn(connection.getHosts() + " reconnecting......");
			connection.connect();
		}else if(event.getState() == KeeperState.AuthFailed || event.getState() == KeeperState.SaslAuthenticated){
			logger.warn(connection.getHosts() + event.toString() + " Permission denied");
		}
	}


}
