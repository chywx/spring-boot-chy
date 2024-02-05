package cn.chendahai.chy.request.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpClientService {

    public void get(String url) throws RestClientException, URISyntaxException, InterruptedException, IOException {
        //创建CloseableHttpClient
        HttpClientBuilder builder = HttpClientBuilder.create();
        CloseableHttpClient client = builder.build();
        HttpUriRequest httpGet = new HttpGet(url);
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String entityStr = EntityUtils.toString(entity, "utf-8");
            System.out.println(entityStr);
        }
        System.out.println(response.toString());
    }

    public void post(String url, Map<String, String> params) throws RestClientException, URISyntaxException, InterruptedException, IOException {
        //创建CloseableHttpClient
        HttpClientBuilder builder = HttpClientBuilder.create();
        CloseableHttpClient client = builder.build();
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> list = new LinkedList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            BasicNameValuePair param = new BasicNameValuePair(entry.getKey(), entry.getValue());
            list.add(param);
        }

        UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");

        httpPost.setEntity(entityParam);
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String entityStr = EntityUtils.toString(entity, "utf-8");
            System.out.println(entityStr);
        }
        System.out.println(response.toString());
    }

    public String postBody(String url, String jsonBody) throws RestClientException, URISyntaxException, InterruptedException, IOException {
        log.info("url:{} params:{}", url, jsonBody);
        CloseableHttpResponse response = null;
        try {
            //创建CloseableHttpClient
            HttpClientBuilder builder = HttpClientBuilder.create();
            CloseableHttpClient client = builder.build();
            HttpPost httpPost = new HttpPost(url);

            StringEntity stringEntity = new StringEntity(jsonBody, "utf-8");
            stringEntity.setContentType("application/json");
            //    stringEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(stringEntity);
            response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String entityStr = EntityUtils.toString(entity, "utf-8");
            log.info("response:{}", entityStr);
            return entityStr;
        } catch (Exception e) {
            log.error("postBody error", e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    log.error("postBody error", e);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        String jsonBody = "{\n" +
                "    \"msg_type\": \"text\",\n" +
                "    \"content\": {\"text\":\"数据 chy request example\"}}\n" +
                "}";
        new HttpClientService().postBody("https://open.larksuite.com/open-apis/bot/v2/hook/23a9722e-9de5-4898-9659-990df2783489", jsonBody);
    }

}
