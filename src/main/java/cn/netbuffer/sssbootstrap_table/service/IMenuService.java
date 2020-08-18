package cn.netbuffer.sssbootstrap_table.service;

import cn.netbuffer.sssbootstrap_table.model.Menu;

public interface IMenuService {
	void addMenu(Menu m);
	Menu save(Menu m);
	Menu findOne(Long id);
	void delete(Long id);
	void deleteTwiceTest(Long id);
}