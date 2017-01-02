package cn.com.ttblog.sssbootstrap_table;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml"})
public class TestRedis {

    private static final Logger LOG= LoggerFactory.getLogger(TestRedis.class);

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testPing(){
        Jedis jedis=new Jedis("127.0.0.1");
        LOG.debug("jedis.ping():{}",jedis.ping());
    }

    @Test
    public void test(){
        LOG.debug("redisTemplate.getClientList():{}",redisTemplate.getClientList());
        LOG.debug("redisTemplate.keys(\"*\"):{}",redisTemplate.keys("*"));
    }

    @Test
    public void testAdd(){
        Object result=redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key  = serializer.serialize("username");
                byte[] name = serializer.serialize("java");
                return redisConnection.setNX(key, name);
            }
        });
        LOG.debug("redis add result:{}",result);
    }

}
