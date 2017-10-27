package cn.com.ttblog.sssbootstrap_table.service;

import cn.com.ttblog.sssbootstrap_table.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public interface IUserService {
	User getUserById(long userId);
	User getUserByName(String userName);
	Page<User> getUserByName(String userName, Pageable page);
	void addUser(User user);
	List<User> getUserList(String order, int limit, int offset);
	//带有查询条件
	List<User> getUserList(String search, String order, int limit,int offset);
	long getUserListCount();
	long getUserListCount(String search);
	int getNewData();
	List<Map<String, Object>> getDataSum();
	void addUM();
	void addUMtest() throws IllegalArgumentException;
	void deleteById(Long id);
	User updateUserWithOptLock(User updateUser);
	User save(User user);
	Page<User> getUserList(final int page, int size, Sort sort, final Map param);
	Future<User> saveUserAsync(User user);
    void saveUserAsyncReturnVoid(User user);
}