package http;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import com.alibaba.fastjson.JSON;
import model.BaiduModel;

public class Http {

    // 范围半径
    public static int scope = 1000;

    public BaiduModel getInfo(String url, NameValuePair[] para) throws HttpException, IOException {
        HttpClient client = new HttpClient();
        // 使用 GET 方法
        HttpMethod method = new GetMethod(url);
        method.setQueryString(para);
        client.executeMethod(method);
        String response = method.getResponseBodyAsString();
        BaiduModel model = JSON.parseObject(response, BaiduModel.class);
        // 释放连接
        method.releaseConnection();
        return model;
    }
}
