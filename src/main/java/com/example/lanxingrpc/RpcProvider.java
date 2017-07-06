package com.example.lanxingrpc;

import com.example.zookeeper.RpcZkService;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import javax.annotation.Resource;
import java.net.InetAddress;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-21, 下午11:44
 * @desc RPC服务注册类
 */
public class RpcProvider {

    /**
     * 注册的服务名
     */
    private String serviceName;

    @Resource
    private RpcZkService rpcZkService;

    /**
     * 提供服务者都在改节点下注册地址
     */
    private static String PROVIDERS = "/providers";

    public void init() throws Exception {
        if(StringUtils.isEmpty(serviceName)){
            throw new RuntimeException("serviceName can not be null");
        }

        String ip = InetAddress.getLocalHost().getHostAddress();

        String servicePath = serviceName + PROVIDERS + "/" + ip;
        //检查服务提供者是否存在,如果已经存在则不再注册

        Stat stat = rpcZkService.getClient().checkExists().forPath(servicePath);
        if(stat != null){
            return;
        }

        //注册服务
        rpcZkService.getClient().create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(servicePath);
    }

    public void destroy() throws Exception {
        String servicePath = serviceName + PROVIDERS + "/" + InetAddress.getLocalHost().getHostAddress();
        rpcZkService.getClient().delete().deletingChildrenIfNeeded().forPath(servicePath);

    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
