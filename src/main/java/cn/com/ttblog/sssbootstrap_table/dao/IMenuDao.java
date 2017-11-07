package cn.com.ttblog.sssbootstrap_table.dao;

import cn.com.ttblog.sssbootstrap_table.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IMenuDao extends JpaRepository<Menu,Long>,JpaSpecificationExecutor<Menu> {
}