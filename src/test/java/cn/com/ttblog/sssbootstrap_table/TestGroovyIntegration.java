package cn.com.ttblog.sssbootstrap_table;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/spring-lang.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestGroovyIntegration {

    @Test
    public void testInit() {
        System.out.println("init");
    }

}