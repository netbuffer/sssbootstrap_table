package cn.com.ttblog.sssbootstrap_table.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * redis发布订阅:http://docs.spring.io/spring-data/redis/docs/1.8.1.RELEASE/reference/html/#pubsub
 */
@Component(value = "redisMessageListener")
public class RedisMessageListener implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(RedisMessageListener.class);
    public static final String EXPIRE_EVENT = "__:expired";

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
        LOG.info("listener receive redis message,body:{},channel:{}", body, channel);
        if (channel.endsWith(EXPIRE_EVENT)) {
            LOG.info("收到[{}]键过期事件通知", body);
        }
    }

}