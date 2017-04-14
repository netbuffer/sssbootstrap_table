package cn.com.ttblog.sssbootstrap_table.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

@Component("redisService")
public class RedisService {

    private static final Logger LOG= LoggerFactory.getLogger(RedisService.class);
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * exec()方法返回List<Object>中的对象对应事务中执行的每条命令的返回结果
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
                    BoundListOperations boundListOperations=redisOperations.boundListOps(key);
                    for(int i=0;i<length;i++){
                        boundListOperations.rightPop();
                    }
                    return redisOperations.exec();
                }
            });
        }catch (Exception e){
            LOG.error("获取redis-key[{}]对应的数据出错:",key,e.getMessage());
        }
        return null;
    }

    //测试也能取到指定条数
//    public List<Object> get(final String key,final Integer length){
//        LOG.info("get key{},length:{}",key,length);
//        try {
//            return stringRedisTemplate.execute(new SessionCallback<List<Object>>() {
//                @Override
//                public List execute(RedisOperations redisOperations) throws DataAccessException {
//                    List result=new ArrayList();
//                    BoundListOperations boundListOperations=redisOperations.boundListOps(key);
//                    for(int i=0;i<length;i++){
//                        result.add(boundListOperations.rightPop());
//                    }
//                    return result;
//                }
//            });
//        }catch (Exception e){
//            LOG.error("获取redis-key[{}]对应的数据出错:",key,e.getMessage());
//        }
//        return null;
//    }

    //使用pipelined的方式来批量执行操作，不用启用事务也能保证取到的条数
//    public List<Object> get(final String key,final Integer length){
//        LOG.info("get key{},length:{}",key,length);
//        try {
//            return stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
//                @Override
//                public Object execute(RedisOperations redisOperations) throws DataAccessException {
//                    redisOperations.watch(key);
//                    BoundListOperations boundListOperations=redisOperations.boundListOps(key);
//                    for(int i=0;i<length;i++){
//                        boundListOperations.rightPop();
//                    }
//                    return null;
//                }
//            });
//        }catch (Exception e){
//            LOG.error("获取redis-key[{}]对应的数据出错:",key,e.getMessage());
//        }
//        return null;
//    }

    //方式二，使用RedisCallback执行事务操作
//    public List get(final String key,final Integer length){
//        try {
//            //结果返回的是list<byte[]>字节数组
//            final List list= (List) stringRedisTemplate.execute(new RedisCallback<Object>() {
//                @Override
//                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
////                redisConnection.select(1);
//                    StringRedisConnection stringRedisConnection=(StringRedisConnection)redisConnection;
//                    stringRedisConnection.watch(key.getBytes());
//                    stringRedisConnection.multi();
//                    for(int i=0;i<length;i++){
//                        stringRedisConnection.rPop(key);
//                    }
//                    List result=stringRedisConnection.exec();
//                    if(result==null){
//                        return null;
//                    }
//                    int resultSize=result.size();
//                    for(int i=0;i<resultSize;i++){
//                        result.set(i,stringRedisTemplate.getValueSerializer().deserialize((byte[]) result.get(i)));
//                    }
//                    return result;
//                }
//            });
//            return list;
//        }catch (Exception e){
//            LOG.error("获取redis-key[{}]对应的数据出错:{}",key,e.getMessage(),e);
//            return null;
//        }
//    }

    //非事务形式执行，测试执行会出错
//    public List get(final String key,final Integer length){
//        stringRedisTemplate.watch(key);
//        stringRedisTemplate.multi();
//        ListOperations operations=stringRedisTemplate.opsForList();
//        for(int i=0;i<length;i++){
//            operations.rightPop(key);
//        }
//        return stringRedisTemplate.exec();
//    }

}
