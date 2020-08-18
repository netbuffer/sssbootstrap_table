package cn.netbuffer.sssbootstrap_table.webservice;

import javax.annotation.Resource;
import javax.jws.HandlerChain;

import org.springframework.stereotype.Service;

import cn.netbuffer.sssbootstrap_table.model.User;
import cn.netbuffer.sssbootstrap_table.service.IUserService;

@Service("userServiceWebservice")
@HandlerChain(file="cxf-handler-chain.xml")
public class UserServiceEndpoint implements UserServiceWebservice {
	
	@Resource
	private IUserService userService;

	@Override
	public User getUser(Long id) {
//		return new User();
		return userService.getUserById(id);
	}

}
