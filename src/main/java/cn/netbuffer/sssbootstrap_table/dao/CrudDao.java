package cn.netbuffer.sssbootstrap_table.dao;

import cn.netbuffer.sssbootstrap_table.model.CrudModel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CrudDao extends PagingAndSortingRepository<CrudModel, Long>, JpaSpecificationExecutor<CrudModel>,CrudDaoCustom<CrudModel,Long> {

    /*@Query("delete from CrudModel c where c.id = ?1")
    @Modifying
    void deleteById(Long id);*/

    /**
     * 在service层调用才能开启事务
     * @param id
     */
    @Query(value = "delete from t_crud_demo where id = ?1",nativeQuery = true)
    @Modifying
    int deleteById(Long id);

}
