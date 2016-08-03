package server;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import model.Response;
import task.GetCall;

public class Server {

    public static void main(String args[]) {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(manager).build();
        HttpGet httpget = new HttpGet("https:\\www.baidu.com");
        ExecutorService executor = Executors.newCachedThreadPool();
        GetCall task = new GetCall(httpClient, httpget);
        Future<Response> result = executor.submit(task);
        try {
            Response response = result.get();
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
