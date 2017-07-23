package cn.com.ttblog.sssbootstrap_table;

import cn.com.ttblog.sssbootstrap_table.dao.IUserFavoritesDao;
import cn.com.ttblog.sssbootstrap_table.model.UserFavorites;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml"})
@ActiveProfiles(value = "test")
public class TestIUserFavoritiesDao {

	private static Logger logger = LoggerFactory.getLogger(TestIUserFavoritiesDao.class);
	@Resource
	private IUserFavoritesDao userFavoritesDao;

	@Test
	public void testSave(){
		UserFavorites userFavorites=new UserFavorites();
		userFavorites.setGoodsId(1L);
		userFavorites.setUserId(1L);
		userFavorites.setRemark("test");
		userFavoritesDao.save(userFavorites);
	}
}
