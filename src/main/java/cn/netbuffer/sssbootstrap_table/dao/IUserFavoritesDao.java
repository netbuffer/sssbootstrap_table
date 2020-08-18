package cn.netbuffer.sssbootstrap_table.dao;

import cn.netbuffer.sssbootstrap_table.model.UserFavorites;
import cn.netbuffer.sssbootstrap_table.model.UserFavoritesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserFavoritesDao extends JpaRepository<UserFavorites,UserFavoritesPK>,JpaSpecificationExecutor<UserFavorites> {
}