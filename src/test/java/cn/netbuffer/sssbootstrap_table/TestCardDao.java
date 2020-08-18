package cn.netbuffer.sssbootstrap_table;

import cn.netbuffer.sssbootstrap_table.dao.ICardDao;
import cn.netbuffer.sssbootstrap_table.model.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml"})
@ActiveProfiles(value = "test")
public class TestCardDao {

	@Resource
	private ICardDao cardDao;

	@Test
	public void testSave(){
		Card card=new Card();
		card.setCardNo("a");
		card.setUserId(1L);
		cardDao.save(card);
	}

}
