package cn.com.ttblog.sssbootstrap_table;

import cn.com.ttblog.sssbootstrap_table.rpc.service.FooService;
import cn.com.ttblog.sssbootstrap_table.rpc.service.UserRpcService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@WebAppConfiguration
@ContextConfiguration(locations={"classpath:spring/spring-context.xml"})
@ActiveProfiles(profiles = "test")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMotanRpc {

//    @Resource
//    private FooService service;

    @Resource
    private UserRpcService userRpcServicep;

//    @Test
//    public void testSimple(){
//        System.out.printf("service.hello(\"motan\"):%s",service.hello("motan"));
//    }

    @Test
    public void testGetUser(){
//        junit测试会导致超时问题
        System.out.printf("get user:%s",userRpcServicep.getUserById(1));
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
