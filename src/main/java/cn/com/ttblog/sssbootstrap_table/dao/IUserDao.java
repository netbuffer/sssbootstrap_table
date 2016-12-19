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
 * JpaRepository<User,Long> 第一个泛型参数指的是实体类，第二个泛型参数指定实体主键类型
 * JpaSpecificationExecutor<User> 泛型参数为实体类
 */
public interface IUserDao extends JpaRepository<User,Long>,JpaSpecificationExecutor<User> {
	@Query(value = "select u from User u where u.id=:id")
	User getUserById(@Param(value = "id") long userId);
    User findByName(String userName);

	@Query(value = "select * from user ORDER BY CASE WHEN :order = 'asc'  THEN  adddate end ASC,CASE WHEN :order = 'desc' THEN adddate end DESC limit :offset,:limit",nativeQuery = true)
	List<User> getUserList(@Param("order") String order,@Param("limit") int limit, @Param("offset") int offset);

	@Query(value = "select * from user where name like %:search% ORDER BY CASE WHEN :order = 'asc'  THEN  adddate end ASC,CASE WHEN :order = 'desc' THEN adddate end DESC limit :offset,:limit",nativeQuery = true)
	List<User> getUserListQueryByName(@Param("search") String search,@Param("order") String order,@Param("limit") int limit, @Param("offset") int offset);

	@Query(value = "select count (u) from User u")
	long getUserListCount();

	@Query(value = "select count (u) from User u where u.name like %?1%")
	long getUserListCount(String search);

	List<User> findTop5ByPhoneContaining(String phone,Sort sort);

	Page<User> findByNameContaining(String name, Pageable page);

	@Query(value = "select concat('性别:',u.sex) from User u where u.id=:id")
	String querySex(@Param("id") Long id);

	@Query(value = "select count(id) from user where DATE_FORMAT(NOW(),'%Y-%m-%d')=FROM_UNIXTIME(adddate,'%Y-%m-%d')",nativeQuery = true)
	int getNewDate();

	@Query(value = "select count(id) num,FROM_UNIXTIME(adddate,'%Y-%m-%d') adddate from user group by FROM_UNIXTIME(adddate,'%Y-%m-%d')",nativeQuery = true)
	List getDataSum();
//	//带有查询条件
//	public List<User> getUserList(String search, String order, int limit, int offset);

	@Query(value = "select u from User u where u.id = (select max(u2.id) from User u2) ")
	User queryMaxUser();

	@Query(value = "select u from User u where u.name like %:name%")
	List<User> queryUserNameLike(@Param("name") String name);

	@Query(value = "select new cn.com.ttblog.sssbootstrap_table.model.Data(count(u.id),FROM_UNIXTIME(u.adddate,'%Y-%m-%d')) from User u group by FROM_UNIXTIME(u.adddate,'%Y-%m-%d')")
	List getUserSum();

}