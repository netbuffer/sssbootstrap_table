package cn.netbuffer.sssbootstrap_table.util;

import org.apache.http.client.fluent.Async;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpAsyncUtil {

    private static Executor executor= Executors.newFixedThreadPool(10);
    private static Async async = Async.newInstance().use(executor);

    public static void main(String[] args){
        HttpAsyncUtil.get("http://www.baidu.com",new HttpHandlerFutureCallback(null));
    }

    /**
     * 返回Future<Content>
     * @param url
     * @return
     */
    public static Future<Content> get(String url,FutureCallback futureCallback) {
        Request request=Request.Get(url);
        Future<Content> future=async.execute(request,futureCallback);
        return future;
    }

    /**
     * 返回Future<Content>
     * @param url
     * @param jsonData
     * @return
     */
    public static Future<Content> post(String url, String jsonData,FutureCallback futureCallback) {
        Request request=null;
        try {
            //超时3秒
            request=Request.Post(url).connectTimeout(3000).socketTimeout(3000).body(new StringEntity(jsonData)).addHeader("Content-Type","application/json");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Future<Content> future=async.execute(request,futureCallback);
        return future;
    }
}
