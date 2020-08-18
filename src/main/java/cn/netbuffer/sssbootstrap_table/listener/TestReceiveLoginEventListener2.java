package cn.netbuffer.sssbootstrap_table.listener;

import cn.netbuffer.sssbootstrap_table.event.LoginEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TestReceiveLoginEventListener2 implements SmartApplicationListener {
	
	private static final Logger log = LoggerFactory.getLogger(TestReceiveLoginEventListener2.class);

	@Override
	public boolean supportsEventType(final Class<? extends ApplicationEvent> eventType) {
		return eventType == LoginEvent.class;
	}

	@Override
	public boolean supportsSourceType(final Class<?> sourceType) {
		return sourceType == HashMap.class;
	}

	@Override
	@Async
	public void onApplicationEvent(final ApplicationEvent event) {
		log.info("{}收到event:{}" ,this, ((LoginEvent)event).getSource());
	}

	@Override
	public int getOrder() {
		return 2;
	}

	@Override
	public String toString() {
		return "listener2";
	}
}