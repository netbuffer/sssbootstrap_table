package cn.com.ttblog.sssbootstrap_table;

import java.util.Random;
import javax.annotation.Resource;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cn.com.ttblog.sssbootstrap_table.dao.IUserDao;
import cn.com.ttblog.sssbootstrap_table.model.User;
import cn.com.ttblog.sssbootstrap_table.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
// 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml"})
public class TestSpringDataJpa {

	private static Logger logger = LoggerFactory.getLogger(TestSpringDataJpa.class);
	@Resource
	private IUserService userService;
	@Resource 
	private IUserDao userDao;
	// @Before
	// public void before() {
	// ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	// userService = (IUserService) ac.getBean("userService");
	// }

	@Test
	public void testFindById() {
		 User user = userService.getUserById(1);
		 logger.debug("testFindById - user1:{}",user);
	}

	@Test
	public void testFindByName() {
		User user = userService.getUserByName("2");
		logger.debug("testFindByName - user1:{}",user);
	}

	@Test
	@Ignore
	public void testAddUser() {
		for (int i = 0; i < 10; i++) {
			User u = new User();
			u.setAge(i + new Random().nextInt(1));
			u.setAdddate((int)(System.currentTimeMillis() / 1000));
			u.setName("用户:"+i);
			u.setDeliveryaddress("收货地址");
			u.setPhone("1324");
			u.setSex("男");
			userService.addUser(u);
		}
	}
	
	@Test
	@Ignore
	public void testDatacount(){
		logger.info("datacount:{}",userService.getDataSum());
	}
	
	@Test
	@Ignore
	public void testAddUserTran(){
		User u=new User();
		u.setName("事务测试");
		u.setAge(10);
		u.setSex("男");
		u.setPhone("13833422322");
		u.setAdddate((int)(System.currentTimeMillis() / 1000));
		userService.addUser(u);
		logger.info("AopUtils.isAopProxy(userService):{}",AopUtils.isAopProxy(userService));
		//cglib
		logger.info("AopUtils.isCglibProxy(userService):{}",AopUtils.isCglibProxy(userService)); 
		//jdk动态代理
		logger.info("AopUtils.isJdkDynamicProxy(userService):{}",AopUtils.isJdkDynamicProxy(userService)); 
	}
	
	@Test
	@Ignore
	public void testTran(){
		userService.addUM();
	}
	
	@Test
	@Ignore
	public void testTrantest(){
		userService.addUMtest();
	}
	
	@Test
	@Ignore
	public void testSelectCache(){
		//mapper中需要配置<cache />节点，会开启缓存
		logger.debug("select1：{}",userService.getUserById(1));
		logger.debug("select2：{}",userService.getUserById(1));
	}
}
