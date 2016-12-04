package cn.com.ttblog.sssbootstrap_table;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
		PageRequest pr=new PageRequest(0,1,new Sort(Sort.Direction.DESC,"id"));
		Page<User> page=userService.getUserByName("",pr);
		logger.debug("page.getTotalPages():{}",page.getTotalPages());
		logger.debug("page.getTotalElements():{}",page.getTotalElements());
		logger.debug("page.getNumberOfElements():{}",page.getNumberOfElements());
		logger.debug("page.getNumber():{}",page.getNumber());
		logger.debug("page.getSize():{}",page.getSize());
		logger.debug("page.getSort():{}",page.getSort());
		logger.debug("page.getContent():{}",page.getContent());
		//页码从0开始
		PageRequest pr2=new PageRequest(1,1,new Sort(Sort.Direction.DESC,"id"));
		Page<User> page2=userService.getUserByName("",pr2);
	}

	@Test
	public void testSaveUser() {
		User u=new User("图图","男",22,"13288383832","收获地址",(int)System.currentTimeMillis()/1000,"remark");
		userService.addUser(u);
//		userDao.save(u);
	}

	@Test
	public void testUpdateUser(){
		User user=userDao.findOne(1L);
		user.setName("修改");
		user.setAge(20);
		logger.warn("修改后user:{}",user);
		userDao.save(user);
	}

	@Test
	public void testDeleteUser(){
		userDao.delete(19420L);
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
	public void testFindTop5ByPhoneContaining(){
		logger.debug("\r");
		userDao.findTop5ByPhoneContaining("1",new Sort(Sort.Direction.DESC,"adddate"));
		logger.debug("\r");
		Sort.Order order=new Sort.Order(Sort.Direction.ASC,"age");
		userDao.findTop5ByPhoneContaining("1",new Sort(order));
		logger.debug("\r");
		Sort.Order order1=new Sort.Order(Sort.Direction.ASC,"age");
		Sort.Order order2=new Sort.Order(Sort.Direction.DESC,"id");
		List<Sort.Order> orders=new ArrayList<>();
		orders.add(order1);
		orders.add(order2);
		userDao.findTop5ByPhoneContaining("1",new Sort(orders));

	}

}
