package cn.com.ttblog.sssbootstrap_table.event;

import org.springframework.context.ApplicationEvent;

public class LoginEvent extends ApplicationEvent {
	
	public LoginEvent(Object source) {
		super(source);
	}
	
}
