package com.example.builder;

import com.example.model.HttpResult;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-22, 上午11:33
 * @desc 类功能描述
 */
public class HttpResultBuilder {

    /**
     * 返回数据
     */
    private String data = StringUtils.EMPTY;

    /**
     * 返回头信息
     */
    private Header[] headers;

    /**
     * 返回状态
     */
    private Integer status = 200;

    public HttpResultBuilder setData(String data) {
        this.data = data;
        return this;
    }

    public HttpResultBuilder setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public HttpResultBuilder setHeaders(Header[] headers) {
        this.headers = headers;
        return this;
    }

    public HttpResult build(){
        HttpResult result = new HttpResult();

        result.setData(this.data);
        result.setStatus(this.status);
        result.setHeaders(this.headers);

        return result;

    }
}
