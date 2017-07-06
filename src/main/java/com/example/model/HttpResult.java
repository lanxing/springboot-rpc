package com.example.model;

import com.example.builder.HttpResultBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;

import java.io.Serializable;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-22, 上午11:24
 * @desc 类功能描述
 */
public class HttpResult implements Serializable{

    private static final long serialVersionUID = 1810538160493143519L;
    /**
     * 返回状态
     */
    private Integer status = 200;

    /**
     * 返回结果头信息
     */
    private Header[] headers;

    /**
     * 返回数据
     */
    private String data;

    public HttpResult() {
    }

    public HttpResult(Integer status) {
        this.status = status;
        this.data = StringUtils.EMPTY;
    }

    public HttpResult(Integer status, String data) {
        this.status = status;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public static HttpResultBuilder builder(){
        return new HttpResultBuilder();
    }
}
