package cn.com.ttblog.sssbootstrap_table.controller;

import cn.com.ttblog.sssbootstrap_table.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
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

    @GetMapping("/listenableFuture")
    public ListenableFuture<Object> listenableFuture(@RequestParam(value = "param", defaultValue = "0") Integer param) {
        LOGGER.info("param:{}", param);
        ListenableFuture<Object> listenableFuture = demoService.listenableFuture(param);
        SuccessCallback<Object> successCallback = new SuccessCallback<Object>() {
            @Override
            public void onSuccess(Object param) {
                LOGGER.info("异步方法[{}]回调结果:{}", "listenableFuture", param);
            }
        };
        FailureCallback failureCallback = new FailureCallback() {
            @Override
            public void onFailure(Throwable throwable) {
                LOGGER.info("异步方法[{}]执行出错:{}", "listenableFuture", throwable);
            }
        };
        listenableFuture.addCallback(successCallback, failureCallback);
        LOGGER.info("继续执行了");
        return listenableFuture;
    }


}
