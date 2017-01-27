package cn.com.ttblog.sssbootstrap_table.serviceimpl;

import cn.com.ttblog.sssbootstrap_table.dao.IUserDao;
import cn.com.ttblog.sssbootstrap_table.model.User;
import cn.com.ttblog.sssbootstrap_table.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	@Override
	public void addUser(User user) {
		userDao.save(user);
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
}