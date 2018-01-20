package cn.com.ttblog.sssbootstrap_table.controller;

import cn.com.ttblog.sssbootstrap_table.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/async")
public class AsyncController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncController.class);

    @Resource
    private DemoService demoService;

    @GetMapping("/err")
    public String asyncErr(@RequestParam(value = "err", defaultValue = "0") Integer err) {
        LOGGER.info("err:{}", err);
        demoService.invoke(err);
        return "success";
    }


}
