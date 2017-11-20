package cn.com.ttblog.sssbootstrap_table.service;

import cn.com.ttblog.sssbootstrap_table.model.Menu;

public interface IMenuService {
	void addMenu(Menu m);
	Menu save(Menu m);
	Menu findOne(Long id);
	void delete(Long id);
	void deleteTwiceTest(Long id);
}