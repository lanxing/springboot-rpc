package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-22, 上午9:19
 * @desc 类功能描述
 */
@Configuration
@ImportResource(locations = {"classpath:rpc-provider.xml", "classpath:rpc-consumer.xml"})
public class RpcConfig {
}
