package cn.netbuffer.sssbootstrap_table.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * redis发布订阅:http://docs.spring.io/spring-data/redis/docs/1.8.1.RELEASE/reference/html/#pubsub
 */
@Component(value = "chatroomListener")
public class ChatRedisMessageListener {
    private static final Logger LOG= LoggerFactory.getLogger(ChatRedisMessageListener.class);
    public void handleMessage(String message,String channel) {
        LOG.info("chatroomListener receive redis msg :{},channel:{}",message,channel);
    }
}