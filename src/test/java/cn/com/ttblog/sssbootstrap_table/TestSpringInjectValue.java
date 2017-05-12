package cn.com.ttblog.sssbootstrap_table;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml","classpath:spring/spring-mvc.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "test")
public class TestSpringInjectValue {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestSpringInjectValue.class);

	@Value(value = "${jdbc.driver}")
	private String jdbcDriver;

	@Test
	public void test(){
		LOGGER.info("jdbcDriver:{}",jdbcDriver);
	}

}