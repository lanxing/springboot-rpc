package com.example;

import com.alibaba.fastjson.JSON;
import com.example.lanxingrpc.RpcConsumer;
import com.example.model.HttpResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-23, 下午2:38
 * @desc 类功能描述
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RpcConsumerTest {

    @Resource
    private RpcConsumer rpcConsumerTest;

    @Test
    public void test() throws IOException, URISyntaxException {
        HttpResult result = rpcConsumerTest.doGet(null);

        System.out.println(JSON.toJSONString(result));

        result = rpcConsumerTest.doPost(null);

        System.out.println(JSON.toJSONString(result));
    }
}
