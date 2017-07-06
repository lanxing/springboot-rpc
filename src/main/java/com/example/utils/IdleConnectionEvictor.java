package com.example.utils;

import org.apache.http.conn.HttpClientConnectionManager;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-22, 上午11:11
 * @desc 类功能描述
 */
public class IdleConnectionEvictor {

    /**
     * 连接管理
     */
    private HttpClientConnectionManager connectionManager;

    /**
     * 间隔时间
     */
    private long sleepTimeMs;

    /**
     * 闲置时间
     */
    private long maxIdleTimeMs;

    private IdleConnectionEvictor idleConnectionEvictor = null;

    public IdleConnectionEvictor(HttpClientConnectionManager connectionManager, long sleepTimeMs, long maxIdleTimeMs) {
        this.connectionManager = connectionManager;
        this.sleepTimeMs = sleepTimeMs;
        this.maxIdleTimeMs = maxIdleTimeMs;
    }

    public void init(){
        idleConnectionEvictor = new IdleConnectionEvictor(connectionManager, sleepTimeMs, maxIdleTimeMs);
    }
}
