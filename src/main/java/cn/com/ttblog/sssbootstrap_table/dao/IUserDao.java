package cn.com.ttblog.sssbootstrap_table.dao;

import cn.com.ttblog.sssbootstrap_table.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * JpaRepository支持接口规范方法名查询。意思是如果在接口中定义的查询方法符合它的命名规则，就可以不用写实现
 * find+全局修饰+By+实体的属性名称+限定词+连接词+ …(其它实体属性)+OrderBy+排序属性+排序方向
 * http://blog.csdn.net/WANTAWAY314/article/details/52945978
 */
public interface IUserDao extends JpaRepository<User,Long>,JpaSpecificationExecutor<User> {
	@Query(value = "from User u where u.id=:id")
	User getUserById(@Param(value = "id") long userId);
    User findByName(String userName);
//	public User getUserByName(String userName);
//	public void addUser(User user);

	@Query(value = "select * from user ORDER BY CASE WHEN :order = 'asc'  THEN  adddate end ASC,CASE WHEN :order = 'desc' THEN adddate end DESC limit :offset,:limit",nativeQuery = true)
	List<User> getUserList(@Param("order") String order,@Param("limit") int limit, @Param("offset") int offset);

//	@Query(value = "select * from user limit :offset,:limit",nativeQuery = true)
//	List<User> getUserList(@Param("limit") int limit, @Param("offset") int offset, Sort sort);

	@Query(value = "select count (u) from User u")
	long getUserListCount();

	List<User> findTop5ByPhoneContaining(String phone,Sort sort);

	Page<User> findByNameContaining(String name, Pageable page);
//	//带有查询条件
//	public List<User> getUserList(String search, String order, int limit, int offset);
//	public long getUserListCount();
//	public int getNewData();
//	public List<Map<String, Object>> getDataSum();
//	public void addUM();
//	public void addUMtest() throws IllegalArgumentException;
//	public void deleteById(Long id);
}