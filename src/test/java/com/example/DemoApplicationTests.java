package com.example;

import com.alibaba.fastjson.JSONObject;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	private CuratorFramework client;

	private String path = "/zk/book";


	@Before
	public void beforeWork(){
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		this.client = CuratorFrameworkFactory.builder()
				.connectString("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183")
				.sessionTimeoutMs(5000)
				.retryPolicy(retryPolicy)
				.namespace("base")
				.build();
		this.client.start();
	}

	@Test
	public void contextLoads() {
		System.out.println("dddd");
	}

	@Test
	public void createNode(){

		try {
			client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, "lanxing".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteNode(){
		try {
			client.delete().guaranteed().forPath(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getNodeData(){
		Stat stat = new Stat();
		try {
			client.getData().storingStatIn(stat).usingWatcher(new CuratorWatcher() {
				@Override
				public void process(WatchedEvent watchedEvent) throws Exception {
					if (watchedEvent.getType() == Watcher.Event.EventType.NodeCreated){
						System.out.println(JSONObject.toJSONString(watchedEvent));
					}
				}
			}).forPath(path);
			Stat exitStat = client.checkExists().forPath(path + "/gaofan");
			if(exitStat != null){
				client.delete().forPath(path + "/gaofan");
			}
			client.create().creatingParentsIfNeeded().forPath(path + "/gaofan");
			System.out.println(JSONObject.toJSONString(stat));
			Thread.sleep(3000L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
