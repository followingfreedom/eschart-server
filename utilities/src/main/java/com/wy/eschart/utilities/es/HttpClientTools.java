package com.wy.eschart.utilities.es;

import com.google.common.collect.Lists;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangyang on 2016/11/25.
 * HttpClient  version 4.5.2
 */
@Component
public class HttpClientTools {

    /**
     * get请求
     *
     * @param url
     * @param timeout 超时
     * @return
     * @throws Exception
     */
    public List get(String url, int timeout) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
            get.setConfig(requestConfig);
            HttpResponse response = client.execute(get);
            return Lists.newArrayList(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
        }
    }

    /**
     * post 请求
     *
     * @param url
     * @param body        json string
     * @param contentType
     * @param timeout     超时
     * @return List index 0 is response code index 1 is response content
     * @throws Exception
     */
    public List post(String url, String body, ContentType contentType, int timeout) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
            StringEntity entity = new StringEntity(body, contentType);
            httpPost.setEntity(entity);
            HttpResponse response = client.execute(httpPost);
            return Lists.newArrayList(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity()));
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "http://192.168.1.202:9200/ares_request_52/_search";
//        System.out.println(new HttpClientTools().get(url, 60000));
//        Map<String, String> map = new HashMap<>();
//        map.put("name", "12121hehhehheheheh");
//        String name = "{\"name\":\"8d8888d88d----2\"}";
        String body = "{\n" +
                "  \"size\": 0, \n" +
                "  \"aggs\": {\n" +
                "    \"aggstest\": {\n" +
                "      \"date_histogram\": {\n" +
                "        \"field\": \"data_receive_at\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        String body2 = "{\n" +
                "  \"size\": 1, \n" +
                "  \"aggs\": {\n" +
                "    \"aggstest\": {\n" +
                "      \"terms\": {\n" +
                "        \"field\": \"response_code\"\n" +
                "      },\n" +
                "      \"aggs\": {\n" +
                "        \"resHits\":{\n" +
                "          \"terms\":{\n" +
                "            \"field\": \"response_hits.code\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        String url2 = "http://192.168.1.202:9200/_cat/indices";
        String result = (String) new HttpClientTools().get(url2, 6000).get(1);
        System.out.println(result.split("\n")[0].split(" ")[2]);
        List list = new HttpClientTools().post(url, body, ContentType.APPLICATION_JSON, 6000);
        System.out.println(list);
//        System.out.println(new HttpClientTools().get(url));
    }
}
