package cn.com.ttblog.sssbootstrap_table.event;

import org.springframework.context.ApplicationEvent;

public class TaskEvent extends ApplicationEvent {
    public TaskEvent(Object source) {
        super(source);
    }
}
