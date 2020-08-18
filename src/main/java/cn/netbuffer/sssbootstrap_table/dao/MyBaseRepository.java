package cn.netbuffer.sssbootstrap_table.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;

/**
 * 其它接口可以继承此接口
 * @param <T>实体类型
 * @param <ID>主键 类型
 */
@NoRepositoryBean
interface MyBaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
  T findOne(ID id);
}
