package com.example.rpctest;

import com.alibaba.fastjson.JSON;
import com.example.zookeeper.RpcZkService;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-23, 上午9:39
 * @desc 类功能描述
 */
public class ZkWatcherTest implements Watcher {

    private String servicePath = "/test";

    @Resource
    private RpcZkService rpcZkService;

    public void init() throws Exception {
        List<String> providers = rpcZkService.getClient().getChildren().usingWatcher(this).forPath(servicePath);

        System.out.println(JSON.toJSONString(providers));

    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(JSON.toJSONString(event));

        if(event.getType() == Event.EventType.NodeChildrenChanged){
            try {
                List<String> providers = rpcZkService.getClient().getChildren().usingWatcher(this).forPath(servicePath);
                System.out.println(JSON.toJSONString(providers));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
