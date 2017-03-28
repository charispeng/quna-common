package com.quna.common.zookeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;

public class BasicZooKeeperByteOperator extends BasicZooKeeperOperator implements ZooKeeperByteOperator{
	public BasicZooKeeperByteOperator(){}
	public BasicZooKeeperByteOperator(ZooKeeperConnection connection){
		super(connection);
	}
	public boolean create(String path, byte[] data, CreateMode createMode) throws Exception {
		String[] paths		= path.split("\\/");
		String createPath	= "";
		for(String tmpPath : paths){
			if(!StringUtils.hasText(tmpPath)){continue;}			
			createPath		+="/" + tmpPath;		
			Stat stat		= this.connection.getZooKeeper().exists(createPath, true);
			if(null != stat){
				continue;
			}
			this.connection.getZooKeeper().create(createPath, data, Ids.OPEN_ACL_UNSAFE, createMode);
		}
		return true;
	}
	
	public byte[] getData(String path) throws Exception {
		if(null != exists(path)){
			byte[] data	= this.connection.getZooKeeper().getData(path,true,null);
			return data;
		}
		return null;
	}
	
	public byte[] getData(String path,Stat stat) throws Exception {
		if(null != exists(path)){
			byte[] data	= this.connection.getZooKeeper().getData(path,true,stat);
			return data;
		}
		return null;
	}
	public boolean setData(String path, byte[] data, int version, CreateMode createMode) throws Exception {
		Stat stat		= exists(path);
		if(null == stat){
			return create(path, data, createMode);
		}else{
			stat		= this.connection.getZooKeeper().setData(path, data, version);
			return null != stat ? true : false;
		}
	}
	
	public boolean setDataIgnoreVersion(String path, byte[] data, CreateMode createMode) throws Exception {
		Stat stat		= exists(path);
		if(null == stat){
			return create(path, data, createMode);
		}else{
			stat		= this.connection.getZooKeeper().setData(path, data, stat.getVersion());
			return null != stat ? true : false;
		}
	}

	public boolean setDataNotEqual(String path, byte[] data, int version, CreateMode createMode) throws Exception {
		Stat stat		= exists(path);
		if(null == stat){
			return create(path, data, createMode);
		}else{
			byte[] tmpData	= getData(path);
			if(!equal(data, tmpData)){
				stat	= this.connection.getZooKeeper().setData(path, data, version);
				return null != stat ? true : false;
			}
		}
		return true;
	}
	
	public boolean setDataNotEqualIgnoreVersion(String path, byte[] data, CreateMode createMode) throws Exception {
		Stat stat		= exists(path);
		if(null == stat){
			return create(path, data, createMode);
		}else{
			byte[] tmpData	= getData(path);
			if(!equal(data, tmpData)){
				stat	= this.connection.getZooKeeper().setData(path, data, stat.getVersion());
				return null != stat ? true : false;
			}
		}
		return true;
	}
	
	private boolean equal(byte[] data,byte[] otherData){
		if(data == null || otherData == null) return false;
		if(data.length != otherData.length) return false;
		for(int i=0;i<data.length;i++){
			byte by1	= data[i];
			byte by2	= otherData[i];
			if(by1 != by2){
				return false;
			}
		}
		return true;
	}
}
