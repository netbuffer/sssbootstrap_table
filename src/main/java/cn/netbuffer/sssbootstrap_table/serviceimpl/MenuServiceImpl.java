package cn.netbuffer.sssbootstrap_table.serviceimpl;

import cn.netbuffer.sssbootstrap_table.dao.IMenuDao;
import cn.netbuffer.sssbootstrap_table.model.Menu;
import cn.netbuffer.sssbootstrap_table.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements IMenuService{

	@Autowired
	private IMenuDao menuDao;

	@Override
	public void addMenu(Menu m) {
		menuDao.save(m);
	}

	@Override
	public Menu save(Menu m) {
		return menuDao.save(m);
	}

	@Override
	public Menu findOne(Long id) {
		int i=1/0;
		return menuDao.findOne(id);
	}

	@Override
	public void delete(Long id) {
		menuDao.delete(id);
	}

	@Override
	public void deleteTwiceTest(Long id) {
//		重复删除会引发 :No class entity xxx with id xxx exists
		this.delete(id);
		this.delete(id);
	}
}