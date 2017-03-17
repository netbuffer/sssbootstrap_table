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
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
public class TestRedis {

    private static final Logger LOG = LoggerFactory.getLogger(TestRedis.class);

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testPing() {
        Jedis jedis = new Jedis("127.0.0.1");
        LOG.debug("jedis.ping():{}", jedis.ping());
    }

    @Test
    public void test() {
        LOG.debug("redisTemplate.getClientList():{}", redisTemplate.getClientList());
        LOG.debug("redisTemplate.keys(\"*\"):{}", redisTemplate.keys("*"));
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
        LOG.debug("stringRedisTemplate.isExposeConnection():{}", stringRedisTemplate.isExposeConnection());
        ValueOperations<String, String> vop = stringRedisTemplate.opsForValue();
        String key = "string_redis_template";
        String v = "use StringRedisTemplate set k v";
        vop.set(key, v);
        LOG.debug("vop.size(key):{}", vop.size(key));
        String value = vop.get(key);
        Assert.assertEquals(v, value);

        Long dbsize = (Long) stringRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                return stringRedisConnection.dbSize();
            }
        });
        LOG.debug("redis 0-数据库key数量:{}", dbsize);
    }

    @Test
    public void testAdd() {
        Object result = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.select(1);//选择数据库
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("username");
                byte[] name = serializer.serialize("java");
                return redisConnection.setNX(key, name);
            }
        });
        LOG.debug("redis add result:{}", result);
    }

    @Test
    public void testAddUser() {
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();
        User u = new User();
        u.setAge(1 + new Random().nextInt(1));
        u.setAdddate((int) (System.currentTimeMillis() / 1000));
        u.setName("batch-add-user:");
        u.setDeliveryaddress("收货地址");
        u.setPhone("1324");
        u.setSex("男");
        valueOperations.set("user", JSON.toJSONString(u));
        User user = JSON.parseObject(valueOperations.get("user").toString(), User.class);
        LOG.debug("user.getName():{}", user.getName());
    }

    /**
     * test ValueOperations
     */
    @Test
    public void testOpsForValue() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        User u = new User();
        u.setAge(1 + new Random().nextInt(1));
        u.setAdddate((int) (System.currentTimeMillis() / 1000));
        u.setName("value-Operations");
        u.setDeliveryaddress("收货地址");
        u.setPhone("1324");
        u.setSex("男");
        valueOperations.set(u.getName(), u);
        LOG.debug("valueOperations.size(u.getName()):{}", valueOperations.size(u.getName()));
        LOG.debug("valueOperations.get(u.getName()):{}", valueOperations.get(u.getName()));
    }

    /**
     * test ListOperations/BoundListOperations
     */
    @Test
    public void testOpsForList() {
        ListOperations listOperations = redisTemplate.opsForList();
        User u = new User();
        u.setAge(1 + new Random().nextInt(1));
        u.setAdddate((int) (System.currentTimeMillis() / 1000));
        u.setName("userlist-Operations");
        u.setDeliveryaddress("收货地址");
        u.setPhone("1324");
        u.setSex("男");
        listOperations.rightPush("userlist", u);
        LOG.debug("listOperations.size(\"userlist\"):{}", listOperations.size("userlist"));
        LOG.debug("listOperations.range(\"userlist\",0,2):{}", listOperations.range("userlist", 0, 2));
        LOG.debug("listOperations.rightPop(\"userlist\"):{}", listOperations.rightPop("userlist"));
        BoundListOperations boundListOperations = redisTemplate.boundListOps("userlist");
        LOG.debug("boundListOperations.rightPop():{}", boundListOperations.rightPop());
        LOG.debug("boundListOperations.rightPushAll(\"test1\",\"test2\",\"test3\"):{}", boundListOperations.rightPushAll("test1", "test2", "test3"));
        LOG.debug("boundListOperations.size():{}", boundListOperations.size());
        LOG.debug("boundListOperations.range(0,boundListOperations.size()+1):{}", boundListOperations.range(0, boundListOperations.size() + 1));
    }

    /**
     * test SetOperations
     */
    @Test
    public void testOpsForSet() {
        SetOperations setOperations = redisTemplate.opsForSet();
        User u = new User();
        u.setAge(1 + new Random().nextInt(1));
        u.setAdddate((int) (System.currentTimeMillis() / 1000));
        u.setName("userset-Operations");
        u.setDeliveryaddress("收货地址");
        u.setPhone("1324");
        u.setSex("男");
        setOperations.add("userset", u);
        setOperations.add("userset", u);
        LOG.debug("setOperations.size(\"userset\"):{}", setOperations.size("userset"));
        LOG.debug("setOperations.members(\"userset\"):{}", setOperations.members("userset"));
        LOG.debug("setOperations.randomMember(\"userset\"):{}", setOperations.randomMember("userset"));
    }

    /**
     * test setKeySerializer/setValueSerializer
     */
    @Test
    public void testSerialize() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
        User u = new User();
        u.setAge(1 + new Random().nextInt(1));
        u.setAdddate((int) (System.currentTimeMillis() / 1000));
        u.setName("userset-Operations");
        u.setDeliveryaddress("收货地址");
        u.setPhone("1324");
        u.setSex("男");
        redisTemplate.opsForValue().set("user-ser", u);
    }

    @Test
    public void testincrement() {
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.increment("fail_error", 1);
        for (int i = 0; i < 5; i++) {
            valueOperations.increment("fail_error", 1);
        }
        LOG.info("valueOperations.get(\"fail_error\")：{}", valueOperations.get("fail_error"));
    }

    /**
     * 非连接池环境下，事务操作；对于sdr而言，每次操作(例如，get，set)都有会从pool中获取connection
     * 因此在连接池环境下，使用事务需要注意。
     */
    @Test
    public void txUnusedPoolSample() {
        User suser = new User();
        suser.setId(1L);
        suser.setName("aaaaa");
        redisTemplate.watch("user:" + suser.getId());
        redisTemplate.multi();
        ValueOperations<String, User> tvo = redisTemplate.opsForValue();
        tvo.set("user:" + suser.getId(), suser);
        redisTemplate.exec();
    }


    /**
     * 在连接池环境中，需要借助sessionCallback来绑定connection
     */
    @Test
    public void txUsedPoolSample() {
        SessionCallback<User> sessionCallback = new SessionCallback<User>() {
            @Override
            public User execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                User user = new User();
                user.setId(1L);
                user.setName("aaaaa");
                String key = "user:" + user.getId();
                BoundValueOperations<String, User> oper = operations.boundValueOps(key);
                oper.set(user);
                oper.expire(60, TimeUnit.SECONDS);
                operations.exec();
                return user;
            }
        };
        redisTemplate.execute(sessionCallback);
    }


    /**
     * pipeline : 1，正确使用方式
     */
    public void pipelineSample() {
        final byte[] rawKey = redisTemplate.getKeySerializer().serialize("user_total");
        //pipeline
        RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                connection.incr(rawKey);
                connection.incr(rawKey);
                return connection.closePipeline();
            }

        };

        List<Object> results = (List<Object>) redisTemplate.execute(pipelineCallback);
        for (Object item : results) {
            System.out.println(item.toString());
        }
    }

    //pipeline:备用方式
    public void pipelineSampleX() {
        byte[] rawKey = redisTemplate.getKeySerializer().serialize("user_total");
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection redisConnection = factory.getConnection();
        List<Object> results;
        try {
            redisConnection.openPipeline();
            redisConnection.incr(rawKey);
            results = redisConnection.closePipeline();
        } finally {
            RedisConnectionUtils.releaseConnection(redisConnection, factory);
        }
        if (results == null) {
            return;
        }
        for (Object item : results) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void testJdkSerialize(){
        ValueOperations valueOperations=redisTemplate.opsForValue();
        User u = new User();
        u.setAge(1 + new Random().nextInt(1));
        u.setAdddate((int) (System.currentTimeMillis() / 1000));
        u.setName("batch-add-user:");
        u.setDeliveryaddress("收货地址");
        u.setPhone("1324");
        u.setSex("男");
        valueOperations.set("userserialize",u);
    }

    /**
     * 反序列化如果bean中的serialVersionUID指定了，即使bean字段修改也可反序列化回来
     * 如果没有指定serialVersionUID，又修改了bean字段，可能会造成无法反序列化
     */
    @Test
    public void testJdkUnSerialize(){
        ValueOperations valueOperations=redisTemplate.opsForValue();
        User u = (User) valueOperations.get("userserialize");
        System.out.println("user:"+u);
    }
}
