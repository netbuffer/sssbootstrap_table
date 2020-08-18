package cn.netbuffer.sssbootstrap_table.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component(value = "asyncExceptionHandler")
public class AsyncExceptionHandler extends SimpleAsyncUncaughtExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... args) {
        LOGGER.error("调用异步任务出错,method:{},args:{},throwable:{}", method, args, throwable);
    }
}