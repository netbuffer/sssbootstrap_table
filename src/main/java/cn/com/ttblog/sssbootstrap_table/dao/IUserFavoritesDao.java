package cn.com.ttblog.sssbootstrap_table.dao;

import cn.com.ttblog.sssbootstrap_table.model.UserFavorites;
import cn.com.ttblog.sssbootstrap_table.model.UserFavoritesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserFavoritesDao extends JpaRepository<UserFavorites,UserFavoritesPK>,JpaSpecificationExecutor<UserFavorites> {
}