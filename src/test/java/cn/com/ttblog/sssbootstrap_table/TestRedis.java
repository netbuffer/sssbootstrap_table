package cn.com.ttblog.sssbootstrap_table;

import cn.com.ttblog.sssbootstrap_table.model.User;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import javax.annotation.Resource;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml"})
public class TestRedis {

    private static final Logger LOG= LoggerFactory.getLogger(TestRedis.class);

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

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

    //http://www.tuicool.com/articles/3aAbMz
    @Test
    public void testListOperations() {
        String key = "spring";
        ListOperations<String, String> lop = redisTemplate.opsForList();
        RedisSerializer<String> serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        // rt.setDefaultSerializer(serializer);
        lop.leftPush(key, "aaa");
        lop.leftPush(key, "bbb");
        long size = lop.size(key); // rt.boundListOps(key).size();
        Assert.assertEquals(2, size);
    }
    @Test
    public void testStringRedisTemplate() {
        LOG.debug("stringRedisTemplate.isExposeConnection():{}",stringRedisTemplate.isExposeConnection());
        ValueOperations<String, String> vop = stringRedisTemplate.opsForValue();
        String key = "string_redis_template";
        String v = "use StringRedisTemplate set k v";
        vop.set(key, v);
        LOG.debug("vop.size(key):{}",vop.size(key));
        String value = vop.get(key);
        Assert.assertEquals(v, value);

        Long dbsize = (Long) stringRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                return stringRedisConnection.dbSize();
            }
        });
        LOG.debug("redis 0-数据库key数量:{}",dbsize);
    }

    @Test
    public void testAdd(){
        Object result=redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.select(1);//选择数据库
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key  = serializer.serialize("username");
                byte[] name = serializer.serialize("java");
                return redisConnection.setNX(key, name);
            }
        });
        LOG.debug("redis add result:{}",result);
    }

    @Test
    public void testAddUser(){
        ValueOperations valueOperations=stringRedisTemplate.opsForValue();
        User u = new User();
        u.setAge(1 + new Random().nextInt(1));
        u.setAdddate((int)(System.currentTimeMillis() / 1000));
        u.setName("batch-add-user:");
        u.setDeliveryaddress("收货地址");
        u.setPhone("1324");
        u.setSex("男");
        valueOperations.set("user", JSON.toJSONString(u));
        User user= JSON.parseObject(valueOperations.get("user").toString(),User.class);
        LOG.debug("user.getName():{}",user.getName());
    }

    @Test
    public void testincrement(){
        ValueOperations valueOperations=stringRedisTemplate.opsForValue();
        valueOperations.increment("fail_error",1);
        for(int i=0;i<5;i++){
            valueOperations.increment("fail_error",1);
        }
        LOG.info("valueOperations.get(\"fail_error\")：{}",valueOperations.get("fail_error"));
    }
}
