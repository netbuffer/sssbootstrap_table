package cn.com.ttblog.sssbootstrap_table.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * redis发布订阅:http://docs.spring.io/spring-data/redis/docs/1.8.1.RELEASE/reference/html/#pubsub
 */
@Component(value = "listener")
public class RedisMessageListener {
    private static final Logger LOG= LoggerFactory.getLogger(RedisMessageListener.class);
    public void handleMessage(String message,String channel) {
        LOG.info("listener receive redis msg :{},channel:{}",message,channel);
    }
}