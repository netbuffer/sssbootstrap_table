package cn.netbuffer.sssbootstrap_table;

import cn.netbuffer.sssbootstrap_table.dao.CrudDao;
import cn.netbuffer.sssbootstrap_table.dao.IUserDao;
import cn.netbuffer.sssbootstrap_table.model.User;
import cn.netbuffer.sssbootstrap_table.service.CrudService;
import cn.netbuffer.sssbootstrap_table.service.IMenuService;
import cn.netbuffer.sssbootstrap_table.service.IUserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * jpql规范:http://docs.oracle.com/javaee/6/tutorial/doc/bnbtg.html
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 表示继承了SpringJUnit4ClassRunner类
/**
 * 配置文件先去src/main/resources下加载，没有的再去src/test/resources下加载
 */
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml"})
@ActiveProfiles(value = "test")
public class TestSpringDataJpa {

	private static Logger logger = LoggerFactory.getLogger(TestSpringDataJpa.class);
	@Resource
	private IUserService userService;
	@Resource 
	private IUserDao userDao;
	@Autowired
	private CrudService crudService;
	@Autowired
	private CrudDao crudDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private EntityManagerFactory em;
	@Resource
	private IMenuService menuService;

	// @Before
	// public void before() {
	// ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	// userService = (IUserService) ac.getBean("userService");
	// }

	@Test
	public void testJdbcTemplate(){
		jdbcTemplate.execute("SELECT now()");
		jdbcTemplate.queryForObject("SELECT count(*) from user",Long.class);
		jdbcTemplate.queryForList("SELECT * from user limit 10");
	}

//	@Test
//	public void testJdbcTemplate(){
//		Query q=em.createQuery("select u from User where u.id=?");
//		q.setParameter(1,1);
//		q.executeUpdate();
//	}

	@Test
	public void testGetCrudModel(){
		logger.debug("crudmodel:{}",crudService.findOne(1L));
	}

	@Test
	public void testNamedQuery() throws ExecutionException, InterruptedException {
		logger.info("userDao.findByN(\"u\"):{}",userDao.findByN("u"));
	}

	@Test
	public void testGetUserList() {
		List<User> users=userService.getUserList("desc",3,0);
	}

	@Test
	public void testJoin() {
		User user=userDao.testJoin(1L);
		logger.info("user.getCard():{}",user.getCard());
	}

	@Test
	public void testFindById() throws ExecutionException, InterruptedException {
		 User user = userService.getUserById(1);
		 User user2 = userService.getUserById(1);
		 //会只执行1次sql查询
		 logger.debug("testFindById - user1:{}",user);
		 Future<User> fu=userDao.findById(1L);
		logger.debug("fu.get().getName():{}",fu.get().getName());
	}

	@Test
	public void testGetUserByCardNo() {
		logger.debug("userDao.getUserByCardNo:{}",userDao.getUserByCardNo("1"));
	}

	@Test
	public void testFindByNameLike() {
		logger.debug("userDao.findByNameLike:{}",userDao.findByNameLike("1"));
		Pageable pr=new PageRequest(0,3,new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
		logger.debug("userDao.findByNameLike-pr:{}",userDao.findByNameLike("1",pr));
	}

	@Test
	public void testFindByNameIgnoreCaseOrderByIdDesc() {
		logger.debug("userDao.findByNameIgnoreCaseOrderByIdDesc:{}",userDao.findByNameIgnoreCaseOrderByIdDesc("1"));
	}


	@Test
	public void testUpdate() {
		User user = userService.getUserById(1);
		user.getCard().setCardNo("1111111111111111111111");
		user.getAddresses().iterator().next().setProvince("北京");
		userService.addUser(user);
	}

	//乐观锁测试
	@Test
	public void testUpdateUserWithOptLock() {
//		如果version字段被修改过会抛出org.springframework.orm.ObjectOptimisticLockingFailureException错误
	    User u=new User("aaa","男",22,"13288383832","收获地址",(int)(System.currentTimeMillis()/1000),"remark",1);
	    u.setId(19459L);
	    userService.updateUserWithOptLock(u);
	}

	@Test
	public void testFindByName() {
		User user = userService.getUserByName("2");
		logger.debug("testFindByName - user1:{}",user);
		PageRequest pr=new PageRequest(0,1,new Sort(Sort.Direction.DESC,"id"));
		Page<User> page=userService.getUserByName("",pr);
		logger.debug("page.getTotalPages()总页数:{}",page.getTotalPages());
		logger.debug("page.getTotalElements()总记录数:{}",page.getTotalElements());
		logger.debug("page.getNumberOfElements()当前页记录数:{}",page.getNumberOfElements());
		logger.debug("page.getNumber()当前页:{}",page.getNumber());
		logger.debug("page.getSize():{}",page.getSize());
		logger.debug("page.getSort():{}",page.getSort());
		logger.debug("page.getContent()当前页数据:{}",page.getContent());
		//页码page从0开始
		PageRequest pr2=new PageRequest(1,1,new Sort(Sort.Direction.DESC,"id"));
		Page<User> page2=userService.getUserByName("",pr2);
	}

	@Test
	public void testSaveUser() {
		User u=new User("图图","男",22,"13288383832","收获地址",(int)(System.currentTimeMillis()/1000),"remark");
		userService.addUser(u);
//		userDao.save(u);
	}

    @Test
    public void testSaveUserVersion() {
        User u=new User("test","男",22,"13288383832","收获地址",(int)(System.currentTimeMillis()/1000),"remark",1);
        userService.addUser(u);
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
//	@Ignore
	public void testAddUser() {
		for (int i = 0; i < 10; i++) {
			User u = new User();
			u.setAge(i + new Random().nextInt(1)+1);
			u.setAdddate((int)(System.currentTimeMillis() / 1000));
			u.setName("用户:"+i);
			u.setDeliveryaddress("收货地址");
			u.setPhone("1324");
			u.setSex("男");
			userService.addUser(u);
		}
	}

	@Test
	public void testBatchAddUser() {
		List<User> users=new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			User u = new User();
			u.setAge(i+1 + new Random().nextInt(1));
			u.setAdddate((int)(System.currentTimeMillis() / 1000));
			u.setName("batch-add-user:"+i);
			u.setDeliveryaddress("收货地址");
			u.setPhone("1324");
			u.setSex("男");
			users.add(u);
		}
		logger.debug("批量添加的学生列表:{}",users);
		userDao.save(users);
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

	@Test
	public void testSortWithManyFields(){
		Sort sort=new Sort(Sort.Direction.ASC,"age")
				.and(new Sort(Sort.Direction.DESC,"adddate"))
				.and(new Sort(Sort.Direction.DESC,"version"));
		userDao.findTop5ByPhoneContaining("1",sort);
	}

	@Test
	public void testQuerySex(){
		logger.debug("获取sex:{}",userDao.querySex(1L));
	}

	@Test
	public void testGetNewDataCount(){
		logger.debug("getNewDate:{}",userDao.getNewDate());
	}

	@Test
	public void testGetDataSum(){
		logger.debug("testGetDataSum:{}",userDao.getDataSum());
	}

	@Test
	public void testGetMaxIdUser(){
		logger.debug("max user:{}",userDao.queryMaxUser());
	}

	@Test
	public void testQueryUserNameLike(){
		logger.debug("testQueryUserNameLike:{}",userDao.queryUserNameLike("用户"));
	}

	/**
	 * 实现带查询条件的分页
	 */
	@Test
	public void testJpaSpecificationExecutor(){

        Pageable pr=new PageRequest(0,3);
		Specification<User> spec=new Specification<User>() {
			/**
			 *
			 * @param root 查询的实体类
			 * @param criteriaQuery
			 * @param criteriaBuilder 创建Criteria
			 * @return
			 */
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Path path=root.get("id");
				Predicate prd=criteriaBuilder.gt(path,10000);
				return prd;
			}
		};
		Page<User> page=userDao.findAll(spec,pr);

		Specification<User> specs=new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.gt(root.get("id").as(Integer.class),1));
                list.add(criteriaBuilder.like(root.get("name").as(String.class),"%add%"));
                Predicate[] parr=new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(parr));
			}
		};
        List<User> users=userDao.findAll(specs);
        logger.debug("use specs:{},query users:{}",specs,users);
	}

	@Test
	public void testGetUserSum(){
		logger.debug("testGetUserSum:{}",userDao.getUserSum());
	}

	@Test
	public void testCustomDaoMethod(){
		logger.debug("通过自定义方法查询记录数:{}",crudDao.getTotal());
	}

	@Test
	public void testFindUserListBySpecs(){
		HashMap param=new HashMap();
		param.put("province","a");
		param.put("age","2");
		logger.debug("testGetUserSum:{}",userService.getUserList(1,5,new Sort(Sort.Direction.DESC,"adddate"),param));
	}

	@Test
	public void testNestingTransaction(){
		User user=new User("aaa","男",22,"13288383832","收获地址",(int)(System.currentTimeMillis()/1000),"remark",1);
		userService.nestingTransaction(user);
	}

	@Test
	public void testDeleteTwice(){
		menuService.deleteTwiceTest(1L);
	}
//	@Modifying +jpql修改数据
}
