package com.quna.common.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import com.quna.common.serialize.SerializationFactory;
import com.quna.common.serialize.Serializers;

public class ZooKeeperTests {
	private static final String hosts		= "115.28.55.90:2182";
	
	@Test
	public void zookeeper() throws Exception{
		WatchedEventCallback callback		= new BasicWatchedEventCallback(new BasicWatchedEventHandler());
		ZooKeeperConnection connection		= new BasicZooKeeperConnection(hosts, callback);
		connection.connect();
		BasicZooKeeperObjectOperator<String> operator= new BasicZooKeeperObjectOperator<String>(connection);
		operator.setSerialization(SerializationFactory.createSerialization(Serializers.HESSIAN2));
		operator.setData("/monitor/setting/test2", "test".getBytes(),0, new StatCallback(){

			@Override
			public void processResult(int rc, String path, Object ctx, Stat res) {
				System.out.println(rc);
				System.out.println(path);
				System.out.println(ctx);
				System.out.println(res);
			}
			
		}, "test");
		System.out.println(operator.exists("/monitor/setting/test2").getVersion());
		boolean ret							= operator.setDataNotEqualIgnoreVersion("/monitor/setting/test3", "test5", String.class,CreateMode.PERSISTENT);
		System.out.println(ret);
		System.out.println(operator.exists("/monitor/setting/test3").getVersion());
		//Thread.sleep(2000);
		//connection.disconnect();
		
		System.in.read();
	}
	
}
