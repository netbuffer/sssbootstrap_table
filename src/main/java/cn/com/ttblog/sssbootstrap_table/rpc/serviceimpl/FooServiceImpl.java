package cn.com.ttblog.sssbootstrap_table.rpc.serviceimpl;

import cn.com.ttblog.sssbootstrap_table.rpc.service.FooService;

public class FooServiceImpl implements FooService {

	public String hello(String name) {
        System.out.println(name + " invoked rpc service");
        return "hello " + name;
    }
}