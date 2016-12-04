package cn.com.ttblog.sssbootstrap_table.serviceimpl;

import cn.com.ttblog.sssbootstrap_table.dao.IUserDao;
import cn.com.ttblog.sssbootstrap_table.model.User;
import cn.com.ttblog.sssbootstrap_table.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private IUserDao userDao;

	@Override
	public User getUserById(long userId) {
		return userDao.getUserById(userId);
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
		return null;
	}

	@Override
	public long getUserListCount() {
		return userDao.getUserListCount();
	}

	@Override
	public int getNewData() {
		return 0;
	}

	@Override
	public List<Map<String, Object>> getDataSum() {
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

	}
}