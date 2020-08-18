package cn.netbuffer.sssbootstrap_table.service;

import cn.netbuffer.sssbootstrap_table.constant.ConfigConstant;
import cn.netbuffer.sssbootstrap_table.exception.CustomGenericException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

    @Async
    public void setSSEData(SseEmitter sseEmitter) {
        try {
            for (int i = 0; i < 10; i++) {
                sseEmitter.send(DateTime.now().toString(ConfigConstant.DATE_LONG_FORMAT), MediaType.TEXT_PLAIN);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            sseEmitter.completeWithError(e);
            LOGGER.info("error:{}", e.getMessage());
        }
        sseEmitter.complete();
    }
}
