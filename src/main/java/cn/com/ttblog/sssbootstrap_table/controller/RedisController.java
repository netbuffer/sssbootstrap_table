package cn.com.ttblog.sssbootstrap_table.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Date;

/**
 * redis发布订阅机制测试
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

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
        stringRedisTemplate.convertAndSend(channel, j.toJSONString());
        return j;
    }
}
