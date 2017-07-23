package cn.com.ttblog.sssbootstrap_table.dao;

import cn.com.ttblog.sssbootstrap_table.model.UserFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserFavoritesDao extends JpaRepository<UserFavorites,Long>,JpaSpecificationExecutor<UserFavorites> {
}