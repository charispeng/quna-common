package com.quna.common.zookeeper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

import com.quna.common.logger.LogUtil;
import com.quna.common.logger.Logger;

public class BasicZooKeeperConnection implements ZooKeeperConnection,Watcher{
	
	private static Logger logger			= LogUtil.getLoggerFactory(BasicZooKeeperConnection.class).create(BasicZooKeeperConnection.class);
	
	private ZooKeeper zooKeeper;
	
	private String hosts;
	
	private WatchedEventCallback watchedEventCallback;
	
	private long sessionId					= -1;
	
	private int sessionTimeout				= 4000;
	
	private byte[] sessionPasswd;
	
	private boolean canBeReadOnly			= false;
	
	public BasicZooKeeperConnection(){}
	
	public BasicZooKeeperConnection(String hosts,WatchedEventCallback watchedEventCallback){
		this.hosts		= hosts;
		this.watchedEventCallback	= watchedEventCallback;
	}

	public ZooKeeper getZooKeeper() {
		return zooKeeper;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}	
	public String getHosts() {
		return this.hosts;
	}

	public void setWatchedEventCallback(WatchedEventCallback watchedEventCallback) {
		this.watchedEventCallback = watchedEventCallback;
	}
	
	public WatchedEventCallback getWatchedEventCallback(){
		return this.watchedEventCallback;
	}
	
	public void setSessionTimeout(int timeout) {
		this.sessionTimeout	= timeout;		
	}
	public void setSessionId(long id) {
		this.sessionId		= id;
	}
	public void setCanBeReadOnly(boolean canBeReadOnly){
		this.canBeReadOnly	= canBeReadOnly;
	}
	public void setSessionPasswd(byte[] passwd) {
		this.sessionPasswd	= passwd;
	}	
	public void connect() {
		if(this.hosts == null){
			throw new IllegalArgumentException("hosts must be not null");
		}
		if(this.watchedEventCallback == null){
			throw new IllegalArgumentException("watchedEventCallback must be not null");
		}
		boolean isConnected	= false;
		int tryCount		= 3;
		while(!isConnected && tryCount > 0){
			try{
				if(this.sessionId != -1 && null != this.sessionPasswd){
					this.zooKeeper	= new ZooKeeper(hosts, sessionTimeout, this, sessionId, sessionPasswd, canBeReadOnly);
				}else{
					this.zooKeeper	= new ZooKeeper(hosts, sessionTimeout, this, canBeReadOnly);
				}
				tryCount	--;
				isConnected	= true;
			}catch(Exception e){
				logger.warn("zookeeper第"+(3 - tryCount + 1)+"次连接失败!");
			}
		}
		if(!isConnected){
			logger.error(this.hosts+"不能连接,请检查地址是否可用．");
		}
		autoReConnection();
	}

	public void disconnect() {
		if(null != zooKeeper){
			try{
				zooKeeper.close();
			}catch(Exception e){
				logger.error(e);
			}
			ses.shutdownNow();
		}
	}
	
	private ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();	
	private void autoReConnection(){
		ses.scheduleWithFixedDelay(new Runnable() {			
			@Override
			public void run() {
				if(logger.isInfoEnabled()){
					logger.info(zooKeeper.getState().toString());
				}
				if(zooKeeper.getState() != States.CONNECTED){
					try{
						logger.warn( hosts + " " + zooKeeper + " reconnection......");
						connect();
					}catch(Exception e){logger.error(e);}
				}
			}
		}, sessionTimeout, sessionTimeout, TimeUnit.SECONDS);		
	}

	@Override
	public String toString() {
		return "BasicZooKeeperConnection [zooKeeper=" + zooKeeper + ", hosts="
				+ hosts + "]";
	}
	
	public void process(WatchedEvent event) {
		this.watchedEventCallback.watchedEventCallback(event, this);
	}
}
