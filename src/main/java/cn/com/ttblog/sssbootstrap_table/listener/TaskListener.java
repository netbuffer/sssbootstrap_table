package cn.com.ttblog.sssbootstrap_table.listener;

import cn.com.ttblog.sssbootstrap_table.event.TaskEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskListener implements ApplicationListener<TaskEvent> {

    @Override
    public void onApplicationEvent(TaskEvent taskEvent) {
        log.info("receive task event:{}", taskEvent);
    }
}
