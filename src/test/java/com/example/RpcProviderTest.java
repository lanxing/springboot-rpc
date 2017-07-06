package com.example;

import com.alibaba.fastjson.JSON;
import com.example.lanxingrpc.RpcProvider;
import com.example.zookeeper.RpcZkService;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-22, 上午9:28
 * @desc 类功能描述
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RpcProviderTest {

    @Resource
    RpcProvider rpcTest;

    @Resource
    private RpcZkService rpcZkService;

    private Stat stat;

    @Before
    public void beforeClass(){
        stat = new Stat();
    }

    @Test
    public void test(){
        try {
            String data = new String(rpcZkService.getClient().getData().storingStatIn(stat).forPath("/book/get"));
            System.out.println(data);
            System.out.println(JSON.toJSONString(stat));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClient(){
        try {
            String data = new String(rpcZkService.getClient().getData().storingStatIn(stat).forPath("/book/get"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getChildrenNodeTest(){
        try {
            List<String> childrenNodes = rpcZkService.getClient().getChildren().forPath("/book/get/providers");

            System.out.println(JSON.toJSONString(childrenNodes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
