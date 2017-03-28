package com.quna.common.zookeeper;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.AsyncCallback.Children2Callback;
import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.OpResult;
import org.apache.zookeeper.AsyncCallback.ACLCallback;
import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.AsyncCallback.MultiCallback;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.AsyncCallback.VoidCallback;
import org.apache.zookeeper.Op;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class BasicZooKeeperOperator implements ZooKeeperOperator {
	protected ZooKeeperConnection connection;
	
	public BasicZooKeeperOperator(){}
	
	public BasicZooKeeperOperator(ZooKeeperConnection connection){
		this.connection	= connection;
	}
	public void setZooKeeperConnection(ZooKeeperConnection connection){
		this.connection	= connection;
	}
	
	public List<String> getChild(String path) throws Exception {
		List<String> nodes	= this.connection.getZooKeeper().getChildren(path, true);
		if(null == nodes || nodes.isEmpty()){		
			return new ArrayList<String>();
		}
		return nodes;
	}
	
	public List<String> getChild(String path,Stat stat) throws Exception {
		List<String> nodes	= this.connection.getZooKeeper().getChildren(path, true, stat);
		if(null == nodes || nodes.isEmpty()){		
			return new ArrayList<String>();
		}
		return nodes;
	}
	
	public Stat exists(String path) throws Exception {
		Stat stat		= this.connection.getZooKeeper().exists(path, true);
		return stat;
	}
	
	@Override
	public boolean deleteIgnoreVersion(String path) throws Exception {
		Stat stat		= this.connection.getZooKeeper().exists(path, true);
		if(null == stat){
			return true;
		}else{
			connection.getZooKeeper().delete(path, stat.getVersion());
			return true;
		}
	}

	@Override
	public boolean delete(String path, int version) throws Exception {
		connection.getZooKeeper().delete(path, version);
		return true;
	}

	@Override
	public boolean delete(String path, int version, VoidCallback callback,Object args) throws Exception {
		connection.getZooKeeper().delete(path,version,callback,args);
		return true;
	}
	
	public void exists(String path,StatCallback callback,Object ctx){
		this.connection.getZooKeeper().exists(path,true,callback,ctx);
	}
	
	public void create(String path, byte[] data, CreateMode createMode,StringCallback callback,Object ctx){
		this.connection.getZooKeeper().create(path, data, Ids.OPEN_ACL_UNSAFE, createMode,callback,ctx);
	}
	
	public void setData(String path,byte[] data,int version,StatCallback callback,Object ctx){
		this.connection.getZooKeeper().setData(path, data, version, callback, ctx);
	}
	
	public void getChild(String path,Children2Callback callback,Object ctx){
		this.connection.getZooKeeper().getChildren(path, true, callback, ctx);
	}
	
	public void getChild(String path,ChildrenCallback callback,Object ctx){
		this.connection.getZooKeeper().getChildren(path, true, callback, ctx);
	}
	
	public List<OpResult> muti(Iterable<Op> ops) throws Exception{
		List<OpResult> opResults	= this.connection.getZooKeeper().multi(ops);
		return opResults;
	}
	
	public void muti(Iterable<Op> ops,MultiCallback callback,Object ctx){		
		this.connection.getZooKeeper().multi(ops,callback,ctx);
	}
	
	public void acl(String path,Stat stat) throws Exception{
		this.connection.getZooKeeper().getACL(path, stat);
	}
	
	public void acl(String path,Stat stat,ACLCallback callback,Object ctx) throws Exception{
		this.connection.getZooKeeper().getACL(path, stat, callback, ctx);
	}
	
	public void addAuthInfo(String scheme,byte[] auth){
		this.connection.getZooKeeper().addAuthInfo(scheme, auth);
	}
	
	public byte[] getData(String path,Stat stat) throws Exception{
		return this.connection.getZooKeeper().getData(path, true, stat);
	}
	
	public void getData(String path,Stat stat,DataCallback callback,Object ctx){
		this.connection.getZooKeeper().getData(path, true, callback, ctx);
	}
	
	public void getSessionId(){
		this.connection.getZooKeeper().getSessionId();
	}
	
	public void getSessionPasswd(){
		this.connection.getZooKeeper().getSessionPasswd();
	}
	
	public void getSessionTimeout(){
		this.connection.getZooKeeper().getSessionTimeout();
	}
	
	public void register(Watcher watcher){
		this.connection.getZooKeeper().register(watcher);
	}
	
	public void sync(String path,VoidCallback callback,Object ctx){
		this.connection.getZooKeeper().sync(path, callback, ctx);
	}
}