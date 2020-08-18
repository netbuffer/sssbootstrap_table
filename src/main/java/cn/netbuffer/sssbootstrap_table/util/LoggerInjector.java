package cn.netbuffer.sssbootstrap_table.util;

import java.lang.reflect.Field;
import cn.netbuffer.sssbootstrap_table.annotation.EnableLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

/**
 * http://bbs.it-home.org/thread-9927-1-1.html
 */
//@Component
public class LoggerInjector implements BeanPostProcessor {
    public LoggerInjector(){
        LOGGER.warn("init 启动了注入");
    }
    private static final Logger LOGGER=LoggerFactory.getLogger(LoggerInjector.class);

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), new FieldCallback() {
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                // make the field accessible if defined private
                ReflectionUtils.makeAccessible(field);
                if (field.getAnnotation(EnableLog.class) != null) {
                    String simpleName = bean.getClass().getSimpleName();
                    Logger logger = LoggerFactory.getLogger(bean.getClass());
                    field.set(bean, logger);
                    String fullName = bean.getClass().getName();
                    LOGGER.warn("注入logger,simpleName:{},fullName:{}",simpleName,fullName);
                }
            }
        });
        return bean;
    }
}