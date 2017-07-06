package com.example;

import com.alibaba.fastjson.JSON;
import com.example.model.HttpResult;
import com.example.service.HttpClientService;
import com.example.zookeeper.RpcZkService;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-22, 下午12:49
 * @desc 类功能描述
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HttpClientServiceTest {

    @Resource
    private HttpClientService httpClientService;

    @Resource
    private RpcZkService rpcZkService;

    @Test
    public void doGetTest(){
        try {
            HttpResult result = httpClientService.doGet("http://www.baidu.com");

            System.out.println(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void RpcConsumerTest(){
        try {
            List<String> providers = rpcZkService.getClient().getChildren().forPath("/book/get/providers");

            if (CollectionUtils.isNotEmpty(providers)) {
                Random random = new Random();
                String ip = providers.get(random.nextInt(providers.size()));

                HttpResult result = httpClientService.doGet("http://" + ip + ":8080" + "/book/get");
                HttpResult postResult = httpClientService.doPost("http://" + ip + ":8080" + "/book/get");
                System.out.println(JSON.toJSONString(result));
                System.out.println(JSON.toJSONString(postResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
