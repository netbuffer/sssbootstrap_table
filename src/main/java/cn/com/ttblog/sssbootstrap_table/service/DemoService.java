package cn.com.ttblog.sssbootstrap_table.service;

import cn.com.ttblog.sssbootstrap_table.exception.CustomGenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.TimeUnit;

@Service
public class DemoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoService.class);

    @Async
    public void invoke(Object param) {
        LOGGER.info("async invoke param:{}", param);
        if (param.equals(1)) {
            throw new CustomGenericException(1, "err");
        }
    }

    @Async
    public ListenableFuture<Object> listenableFuture(Integer param) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<Object>(param);
    }
}
