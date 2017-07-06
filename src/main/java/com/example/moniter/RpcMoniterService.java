package com.example.moniter;

import com.alibaba.fastjson.JSONObject;
import com.example.zookeeper.RpcZkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-12-01, 上午9:45
 * @desc 类功能描述
 */

@RestController
@RequestMapping("/rpc")
public class RpcMoniterService {

    private static String PROVIDERS = "/providers";

    private static String CONSUMERS = "/consumers";

    @Resource
    private RpcZkService rpcZkService;

    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public String rpcInfo(@RequestParam("serviceName") String serviceName) {
        String realService = serviceName.replace('.', '/');

        JSONObject jsonObject = new JSONObject();

        String providerPath = realService + PROVIDERS;

        String consumerPath = realService + CONSUMERS;

        List<String> providers = null;
        List<String> consumers = null;
        try {
            providers = rpcZkService.getClient().getChildren().forPath(providerPath);
            jsonObject.put("providers", providers);
        } catch (Exception e) {
            jsonObject.put("prividersError", "未找到服务提供者");
        }

        try {
            consumers = rpcZkService.getClient().getChildren().forPath(consumerPath);
            jsonObject.put("consumers", consumers);
        } catch (Exception e) {
            jsonObject.put("consumersError", "未找到服务消费者");
        }

        jsonObject.put("service", serviceName);

        return jsonObject.toJSONString();
    }

//    public static void main(String[] args) {
//        String test = "dd.dd.dd.dd";
//        System.out.println(test.replace('.', '/'));
//    }

}
