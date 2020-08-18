package cn.netbuffer.sssbootstrap_table.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import cn.netbuffer.sssbootstrap_table.model.User;

@WebService
public interface UserServiceWebservice {

	@WebMethod
	public User getUser(@WebParam(name = "id") Long id);
}
