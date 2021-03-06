package cn.netbuffer.sssbootstrap_table.controller;

import cn.netbuffer.sssbootstrap_table.model.User;
import cn.netbuffer.sssbootstrap_table.service.IUserService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Restful Controller, 直接输出内容，不调用template引擎
 * @package cn.netbuffer.sssbootstrap_table.controller
 * @author netbuffer
 */
@RestController
@RequestMapping("/restapi")
public class ApiController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IUserService userService;

	@GetMapping(value = { "user", "/user/{id}", "/user/index/{id}" })
	public User user(@PathVariable("id") Long id) {
		User u=userService.getUserById(id);
		logger.debug("restapi get user id:{},query user:{}",id,u);
		return u;
	}

	@GetMapping(value = "users")
	public Page<User> users(@PageableDefault(page = 1) Pageable pageable) {
		logger.info("get users pageable:{}", ToStringBuilder.reflectionToString(pageable, ToStringStyle.JSON_STYLE));
		return userService.getUserList(pageable);
	}

	@GetMapping(value = "system/prop")
	public String getSystemProp(String prop) {
		//user.dir
		return System.getProperty(prop);
	}

}
