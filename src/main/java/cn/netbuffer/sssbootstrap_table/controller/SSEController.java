package cn.netbuffer.sssbootstrap_table.controller;

import cn.netbuffer.sssbootstrap_table.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sse")
public class SSEController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SSEController.class);

    @Resource
    private DemoService demoService;

    @RequestMapping(method = RequestMethod.GET)
    public SseEmitter handleRequest() {
//      默认超时时间设置  <mvc:async-support default-timeout="5000"
        final SseEmitter emitter = new SseEmitter(-1L);
        LOGGER.info("sse :{}", emitter);
        demoService.setSSEData(emitter);
        return emitter;
    }

}