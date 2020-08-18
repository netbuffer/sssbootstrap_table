package cn.netbuffer.sssbootstrap_table.controller;

import cn.netbuffer.sssbootstrap_table.service.RedisService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * redis发布订阅机制测试
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    private static final Logger LOGGER= LoggerFactory.getLogger(RedisController.class);
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisService redisService;

    /**
     * 发布消息
     * @param channel
     * @param msg
     * @param type
     * @return
     */
    @RequestMapping(value = "send/{channel}",method = RequestMethod.GET)
    public JSONObject send(@PathVariable("channel") String channel,
                           @RequestParam(value = "msg",required = false,defaultValue = "消息结构体") String msg,
                           @RequestParam(value = "type",required = false,defaultValue = "1") Integer type){
        JSONObject j = new JSONObject();
        j.put("type", type);
        j.put("data", msg);
        j.put("time", new Date());
        LOGGER.info("发送到频道[{}]消息:{}",channel,j);
        stringRedisTemplate.convertAndSend(channel, j.toJSONString());
        return j;
    }

    /**
     * 统计db中的key数
     * @param db
     * @return
     */
    @RequestMapping(value = "dbsize/{db}",method = RequestMethod.GET)
    public Long dbsize(@PathVariable(value = "db") final Long db){
        return stringRedisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.select(db.intValue());
                Long dbSize=redisConnection.dbSize();
                LOGGER.debug("查询:{}号数据库的key数:{},redisConnection:{}",db,dbSize,redisConnection);
                return dbSize;
            }
        });
    }

    /**
     * 检测key是否存在
     * @param key
     * @return
     */
    @RequestMapping(value = "key/exist/{key}",method = RequestMethod.GET)
    public Set<String> key(@PathVariable(value = "key") String key){
        Set<String> keys=stringRedisTemplate.keys(key);
        LOGGER.info("检测key:{},keys:{}",key,keys);
        return keys;
    }

    /**
     * 创建key/value测试
     * @param key
     * @param length
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Long list(@RequestParam(value = "key",defaultValue = "list") String key,@RequestParam(value = "length",required = false,defaultValue = "100") Integer length){
        LOGGER.info("创建key:{},长度:{}",key,length);
        ListOperations list=stringRedisTemplate.opsForList();
        String[] arr=new String[length];
        for(int i=0;i<length;i++){
            arr[i]=String.valueOf(i);
        }
        return list.leftPushAll(key,arr);
    }

    /**
     * 非事务形式获取指定条数数据
     * @param key
     * @param length
     * @return
     */
    @RequestMapping(value = "list/pop/{key}",method = RequestMethod.GET)
    public List popList(@PathVariable(value = "key") String key,@RequestParam(value = "length",required = false,defaultValue = "10") Integer length){
        BoundListOperations list=stringRedisTemplate.boundListOps(key);
        List result=new ArrayList();
        for (int i=0;i<length;i++){
            result.add(list.rightPop());
        }
        LOGGER.info("pop key:{},长度:{},数据:{}",key,result.size(),result);
        //多线程并发访问不能保证result一定达到10条记录
        return result;
    }

    /**
     * 以事务形式获取指定条数数据
     * @param key
     * @param length
     * @return
     */
    @RequestMapping(value = "list/pop/{key}/tran",method = RequestMethod.GET)
    public List popListWithTran(@PathVariable(value = "key") String key,@RequestParam(value = "length",required = false,defaultValue = "10") Integer length){
        List list=redisService.get(key,length);
        LOGGER.info("pop:{}的{}条数据:{},list size:{}",key,length,list,list.size());
        return list;
    }
}
