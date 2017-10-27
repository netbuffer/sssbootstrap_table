package cn.com.ttblog.sssbootstrap_table.serviceimpl;

import cn.com.ttblog.sssbootstrap_table.dao.IUserDao;
import cn.com.ttblog.sssbootstrap_table.model.User;
import cn.com.ttblog.sssbootstrap_table.service.IUserService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements IUserService{
	private static final Logger LOG= LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private IUserDao userDao;

	@Override
	public User getUserById(long userId) {
		return userDao.getUserById(userId);
//		return userDao.findOne(userId);
	}

	@Override
	public User getUserByName(String userName) {
		return userDao.findByName(userName);
	}

	@Override
	public Page<User> getUserByName(String userName, Pageable page) {
		return userDao.findByNameContaining(userName,page);
	}

	/**
	 * 1.mysql指定transaction-isolation=SERIALIZABLE级别，方法无需加synchronized也能保证name不会重复( PROPAGATION_REQUIRES_NEW,ISOLATION_DEFAULT)
	 * 2.spring 中指定 isolation="SERIALIZABLE" 隔离级别，方法无需加synchronized也能保证name不会重复
	 * 3.spring 中指定  isolation="READ_COMMITTED" ,方法加锁可以保证name不重复
	 * 4.spring不指定isolation，使用mysql默认的隔离级别，方法加锁，也可以保证name不重复
	 * @param user
	 */
	@Override
	public void addUser(User user) {
		//锁定当前用户名的请求
		synchronized (user.getName().intern()){
//		synchronized (user.getName()){
			User exist=userDao.findByName(user.getName());
			if(exist!=null&&exist.getId()>0){
				return;
			}
			user.setDeliveryaddress("thread:"+Thread.currentThread().getName()+",time:"+new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
			userDao.saveAndFlush(user);
		}
		//事务测试
//		int i=1/0;
	}

	@Override
	public List<User> getUserList(String order, int limit, int offset) {
//		if (order!=null&&order.equals("desc")){
//			return userDao.getUserList(limit,offset,new Sort(Sort.Direction.DESC,"adddate"));
//		}
//		return userDao.getUserList(limit,offset,new Sort(Sort.Direction.ASC,"adddate"));
		return userDao.getUserList(order,limit,offset);
	}

	@Override
	public List<User> getUserList(String search, String order, int limit, int offset) {
		return userDao.getUserListQueryByName(search,order,limit,offset);
	}

	/**
	 *  使用Specification来查询,一对多属性
	 * @param page
	 * @param size
	 * @param sort
	 * @param param
	 * @return
	 */
	public Page<User> getUserList(final int page, int size, Sort sort, final Map param) {
		Pageable pageable= new PageRequest(page,size,sort);
		return userDao.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Predicate ageSearch=criteriaBuilder.greaterThanOrEqualTo(root.get("age").as(Integer.class),Integer.parseInt(param.get("age")==null?"10":param.get("age").toString()));
				Predicate addressSearch=criteriaBuilder.like(root.join("addresses", JoinType.LEFT).get("province").as(String.class),"%"+param.get("province")+"%");
				return criteriaBuilder.or(addressSearch,ageSearch);
			}
		},pageable);
	}


	@Override
	public long getUserListCount() {
		return userDao.getUserListCount();
	}

	@Override
	public long getUserListCount(String search) {
		return userDao.getUserListCount(search);
	}

	@Override
	public int getNewData() {
		return userDao.getNewDate();
	}

	@Override
	public List getDataSum() {
		List datas=userDao.getDataSum();
		LOG.debug("dao层拿到数据:{},size:{}",datas,datas.size());
		if(null!=datas&&datas.size()>0){
			int count=datas.size();
			List<Map<String, Object>> result=new ArrayList<>(count);
			for (int i=0;i<count;i++) {
				LOG.debug("datas.get({}):{}",i,datas.get(i));
				Map<String,Object> m=new HashMap<>();
				m.put("num",((Object[]) datas.get(i))[0]);
				m.put("adddate",((Object[])datas.get(i))[1]);
				result.add(m);
			}
			return result;
		}
		return null;
	}

	@Override
	public void addUM() {

	}

	@Override
	public void addUMtest() throws IllegalArgumentException {

	}

	@Override
	public void deleteById(Long id) {
		userDao.delete(id);
	}

	@Override
	public User updateUserWithOptLock(User updateUser) {
		User user=userDao.selectUserWithOptLock(updateUser.getId());
		user.setName(updateUser.getName());
		try {
			//休眠过程中
			TimeUnit.SECONDS.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return userDao.save(user);
	}

	@Override
	public User save(User user) {
		return userDao.save(user);
	}

	/**
	 * @Async 异步保存用户
	 * @param user
	 * @return
	 */
	@Override
	@Async
	public Future<User> saveUserAsync(User user) {
		LOG.info("start execute save");
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		User savedUser=userDao.save(user);
		LOG.info("execute save success");
//		int i=1/0;
		return new AsyncResult<User>(savedUser);
	}

	/**
	 * @Async 会去spring容器中找默认的task:annotation-driven配置的executor来执行
	 * @param user
	 */
    @Override
    @Async
    public void saveUserAsyncReturnVoid(User user) {
        LOG.info("start execute save");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userDao.save(user);
		//测试出错，事务情况
        if(user.getComments()!=null&&user.getComments().equals("-1")){
			int i=1/0;
		}
        LOG.info("execute save success");
    }

}
