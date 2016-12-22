package task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.Callable;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import com.alibaba.fastjson.JSON;

public class GetCall<T> implements Callable<T> {

    private final CloseableHttpClient httpClient;
    private final HttpContext context;
    private final HttpGet httpget;
    private final Type type;

    public GetCall(CloseableHttpClient httpClient, HttpGet httpget, Type type) {
        this.httpClient = httpClient;
        this.context = HttpClientContext.create();
        this.httpget = httpget;
        this.type = type;
    }

    @Override
    public T call() {
        T t = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpget, context);
            t = JSON.parseObject(response.getEntity().getContent(), type);
            response.close();
        } catch (IOException ex) {
            // Handle I/O errors
        }
        return t;
    }
}