package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-22, 上午9:43
 * @desc 配置类
 */
@Configuration
@ImportResource(locations = {"classpath:spring-bean.xml", "classpath:http-client.xml", "classpath:zkclient.xml"})
public class ConfigClass {
}
