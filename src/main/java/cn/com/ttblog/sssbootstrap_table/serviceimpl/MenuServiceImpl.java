package cn.com.ttblog.sssbootstrap_table.serviceimpl;

import cn.com.ttblog.sssbootstrap_table.dao.IMenuDao;
import cn.com.ttblog.sssbootstrap_table.model.Menu;
import cn.com.ttblog.sssbootstrap_table.service.IMenuService;
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
}