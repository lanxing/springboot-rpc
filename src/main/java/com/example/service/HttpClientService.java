package com.example.service;

import com.example.model.HttpResult;
import com.google.common.collect.Lists;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-22, 上午10:17
 * @desc 类功能描述
 */
@Service("httpClientService")
public class HttpClientService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig requestConfig;

    /**
     * get请求
     * @param url
     * @return
     * @throws IOException
     */
    public HttpResult doGet(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(this.requestConfig);

        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                return HttpResult.builder()
                        .setStatus(response.getStatusLine().getStatusCode())
                        .setData(EntityUtils.toString(response.getEntity(), "utf-8"))
                        .setHeaders(response.getAllHeaders())
                        .build();
            } else {
                return HttpResult.builder()
                        .setStatus(response.getStatusLine().getStatusCode())
                        .setData(EntityUtils.toString(response.getEntity(), "utf-8"))
                        .setHeaders(response.getAllHeaders())
                        .build();
            }
        }finally {
            if(response != null){
                response.close();
            }
        }
    }

    /**
     * 带参数的get请求
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public HttpResult doGet(String url, Map<String, String> params) throws URISyntaxException, IOException {

        URIBuilder uriBuilder = new URIBuilder(url);

        for (String key : params.keySet()){
            uriBuilder.addParameter(key, params.get(key));
        }

        return this.doGet(uriBuilder.build().toString());
    }

    /**
     * 带参数的post请求
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public HttpResult doPost(String url, Map<String, String> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        if(params != null){
            List<NameValuePair> parameters = Lists.newArrayList();
            for (String key : params.keySet()){
                parameters.add(new BasicNameValuePair(key, params.get(key)));
            }

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "utf-8");
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpPost);

            return HttpResult.builder()
                    .setStatus(response.getStatusLine().getStatusCode())
                    .setData(EntityUtils.toString(response.getEntity(), "utf-8"))
                    .setHeaders(response.getAllHeaders())
                    .build();

        }finally {
            if(response != null){
                response.close();
            }
        }
    }

    /**
     * 无参数的post请求
     * @param url
     * @return
     * @throws IOException
     */
    public HttpResult doPost(String url) throws IOException {
        return doPost(url, null);
    }
}
