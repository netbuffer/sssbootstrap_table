package cn.com.ttblog.sssbootstrap_table.listener;

import cn.com.ttblog.sssbootstrap_table.event.LoginEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component  
public class TestReceiveLoginEventListener1 implements SmartApplicationListener {  
  
	private static final Logger log = LoggerFactory.getLogger(TestReceiveLoginEventListener1.class);

	@Override
	public boolean supportsEventType(final Class<? extends ApplicationEvent> eventType) {
		return eventType == LoginEvent.class;
	}

	@Override
	public boolean supportsSourceType(final Class<?> sourceType) {
		//这里传具体的类才生效，测试使用Map.class无效
		return sourceType == HashMap.class;
	}

	@Override
	public void onApplicationEvent(final ApplicationEvent event) {
		log.info("{}收到event:{}" ,this, event.getSource());
		//同步处理事件下,在发布事件的地方try-catch可以捕获到错误
//		throw new RuntimeException("error");
	}
	
    @Override  
    public int getOrder() {  
        return 1;  
    }

	@Override
	public String toString() {
		return "listener1";
	}
}