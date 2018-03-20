package cn.com.ttblog.sssbootstrap_table.component;

import cn.com.ttblog.sssbootstrap_table.event.TaskEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//@Component
public class Timing {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(cron = "0/10 * * * * ?")
    public void pub() {
        for (int i = 0, len = 100; i < len; i++) {
            applicationEventPublisher.publishEvent(new TaskEvent(this));
        }
    }
}
