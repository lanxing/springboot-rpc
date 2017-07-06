package com.example.lanxingrpc;

import com.example.model.HttpResult;
import com.example.service.HttpClientService;
import com.example.zookeeper.RpcZkService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-22, 下午2:47
 * @desc Rpc消费者类,实现了Zookeeper的watcher接口,用于监听服务提供者的IP列表变化
 */
public class RpcConsumer implements Watcher{

    /**
     * 要消费的服务名
     */
    private String serviceName;

    /**
     * 服务提供者的ip,为了保证一个客户端调用时尽量落在一台机器上,在初始化时随机从所有服务提供者IP列表里取出一个
     * 客户端后边的所有操作都落在该台机器上
     */
    private String providerIp = null;

    /**
     * 所有服务提供者IP列表,初始化时从zookeeper节点中获取,之后动态监听列表变化,从而动态更新服务提供者列表
     */
    private List<String> allProviders = null;

    @Resource
    private HttpClientService httpClientService;

    @Resource
    private RpcZkService rpcZkService;

    /**
     * 端口号
     */
    private int port = 80;

    private static String PROVIDERS = "/providers";

    private static String CONSUMERS = "/consumers";

    /**
     * 初始化消费者,主要获取服务提供者的IP列表,并从中任选一个作为提供者IP,同事注册监听服务提供节点的监听任务
     * @throws Exception
     */
    public void init() throws Exception {
        String servicePath = serviceName + PROVIDERS;

        Stat existStat = rpcZkService.getClient().checkExists().forPath(servicePath);

        if(existStat == null){
            throw new Exception("没有" + serviceName + "的服务提供者");
        }

        List<String> providers = rpcZkService.getClient().getChildren().usingWatcher(this).forPath(servicePath);
        if(CollectionUtils.isEmpty(providers)){
            throw new Exception("没有" + serviceName + "的服务提供者");
        }

        this.allProviders = providers;

        this.providerIp = providers.get(new Random().nextInt(providers.size()));

        String consumerPath = serviceName + CONSUMERS + "/" + InetAddress.getLocalHost().getHostAddress();
        Stat stat = rpcZkService.getClient().checkExists().forPath(consumerPath);
        if(stat != null){
            return;
        }

        rpcZkService.getClient().create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(consumerPath);
    }

    private boolean checkProviderIp(){
        return true;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * restful的get请求
     * @param params
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public HttpResult doGet(Map<String, String> params) throws IOException, URISyntaxException {
        String url = "http://" + this.providerIp + ":" + this.port + serviceName;
        if(params == null){
            return httpClientService.doGet(url);
        }else {
            return httpClientService.doGet(url, params);
        }
    }

    /**
     * restful的post请求
     * @param params
     * @return
     * @throws IOException
     */
    public HttpResult doPost(Map<String, String> params) throws IOException {
        String url = "http://" + this.providerIp + ":" + this.port + serviceName;
        if(params == null){
            return httpClientService.doPost(url);
        }else {
            return httpClientService.doPost(url, params);
        }
    }

    /**
     * 消费者节点设置的是临时节点,但由于zk本身存在延迟,所以这里执行自动删除
     */
    public void destroy() throws Exception {
        String consumerPath = serviceName + CONSUMERS + "/" + InetAddress.getLocalHost().getHostAddress();
        rpcZkService.getClient().delete().deletingChildrenIfNeeded().forPath(consumerPath);
    }

    @Override
    public void process(WatchedEvent event) {

        if(event.getType() == Event.EventType.NodeChildrenChanged){
            //只监听子节点变化
            try {

                //重新获取服务提供者列表,并添加监听事件,因为zookeeper的监听事件是一次性的,所以每次都需要重新设置
                allProviders = rpcZkService.getClient().getChildren().usingWatcher(this).forPath(event.getPath());

                if (!allProviders.contains(this.providerIp)){
                    //如果当前列表不包括当前提供服务的IP,则更新当前提供服务的IP
                    this.providerIp = allProviders.get(new Random().nextInt(allProviders.size()));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
