package task;

import java.io.IOException;
import java.util.concurrent.Callable;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import com.alibaba.fastjson.JSON;
import model.Response;

public class GetCall implements Callable<Response> {

    private final CloseableHttpClient httpClient;
    private final HttpContext context;
    private final HttpGet httpget;

    public GetCall(CloseableHttpClient httpClient, HttpGet httpget) {
        this.httpClient = httpClient;
        this.context = HttpClientContext.create();
        this.httpget = httpget;
    }

    @Override
    public Response call() {
        Response result = new Response();
        try {
            CloseableHttpResponse response = httpClient.execute(httpget, context);
            try {
                HttpEntity entity = response.getEntity();
                result = JSON.parseObject(entity.getContent(), Response.class);
            } finally {
                response.close();
            }
        } catch (ClientProtocolException ex) {
            // Handle protocol errors
        } catch (IOException ex) {
            // Handle I/O errors
        }
        return result;
    }
}