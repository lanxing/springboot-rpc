package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-23, 上午9:45
 * @desc 类功能描述
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZkWatcherTest {

    @Resource
    private com.example.rpctest.ZkWatcherTest zkWatcherTest;

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(Integer.MAX_VALUE);
    }
}
