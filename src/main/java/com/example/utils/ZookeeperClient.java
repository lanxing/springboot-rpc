//package com.example.utils;
//
//import org.apache.curator.RetryPolicy;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//
///**
// * @author muqi
// * @version 1.00
// * @date 2016-11-22, 上午9:34
// * @desc 类功能描述
// */
//public class ZookeeperClient {
//    public static CuratorFramework client = null;
//
//    private static String basePath = "rpc";
//
//    private static String servers = "30.10.211.237:2181,30.10.211.237:2182,30.10.211.237:2183";
//
//    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//
//    static {
//        client = CuratorFrameworkFactory.builder()
//                .connectString(servers)
//                .sessionTimeoutMs(50000)
//                .retryPolicy(retryPolicy)
//                .namespace(basePath)
//                .build();
//        client.start();
//    }
//}
