package cn.netbuffer.sssbootstrap_table.util;

import org.apache.http.client.fluent.Content;
import org.apache.http.concurrent.FutureCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import java.nio.charset.Charset;

public class HttpHandlerFutureCallback implements FutureCallback<Content>{

    private JdbcTemplate jdbcTemplate;

    public HttpHandlerFutureCallback(){}
    public HttpHandlerFutureCallback(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public void completed(Content content) {
//        jdbcTemplate.execute("");
        System.out.println("execute callback:"+content.asString(Charset.forName("utf-8")));
    }

    @Override
    public void failed(Exception e) {
//        jdbcTemplate.execute("");
        System.out.println("execute failed:"+e.getMessage());
    }

    @Override
    public void cancelled() {
        System.out.println("execute cancel!");
    }
}
