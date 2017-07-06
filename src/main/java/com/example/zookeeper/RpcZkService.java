package com.example.zookeeper;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-24, 上午9:26
 * @desc 类功能描述
 */
public class RpcZkService {

    private String basePath;

    private CuratorFramework client;

    private List<String> servers;

    public void init() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        if(CollectionUtils.isEmpty(servers)){
            throw new Exception("servers不能为null");
        }

        String jsonServiers = JSONObject.toJSONString(this.servers);
        String listServers = jsonServiers.substring(1, jsonServiers.length() - 1).replace("\"", "");

        client = CuratorFrameworkFactory.builder()
                .connectString(listServers)
                .sessionTimeoutMs(50000)
                .retryPolicy(retryPolicy)
                .namespace(basePath)
                .build();
        client.start();
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public CuratorFramework getClient() {
        return client;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

//    public static void main(String[] args) {
//        List<String> tmp = Lists.newArrayList("1111:111", "22:222", "333:333");
//        String jsonServiers = JSONObject.toJSONString(tmp);
//        String listServers = jsonServiers.substring(1, jsonServiers.length() - 1).replace("\"", "");
//        System.out.println(listServers);
//
//    }
}
