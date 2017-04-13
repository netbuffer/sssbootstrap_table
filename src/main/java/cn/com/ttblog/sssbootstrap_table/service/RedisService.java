package cn.com.ttblog.sssbootstrap_table.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

@Component("redisService")
public class RedisService {

    private static final Logger LOG= LoggerFactory.getLogger(RedisService.class);
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取指定的条数数据，以事务方式执行
     * @param key
     * @param length
     * @return
     */
    public List<Object> get(final String key,final Integer length){
        LOG.info("get key{},length:{}",key,length);
        try {
            return stringRedisTemplate.execute(new SessionCallback<List<Object>>() {
                @Override
                public List execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.watch(key);
                    redisOperations.multi();
                    ListOperations listOperations=redisOperations.opsForList();
                    for(int i=0;i<length;i++){
                        listOperations.rightPop(key);
                    }
                    return redisOperations.exec();
                }
            });
        }catch (Exception e){
            LOG.error("获取redis-key[{}]对应的数据出错:",e.getMessage());
        }
        return null;
    }
}
