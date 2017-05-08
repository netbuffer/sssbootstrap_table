package cn.com.ttblog.sssbootstrap_table.controller;

import cn.com.ttblog.sssbootstrap_table.Constant.ConfigConstant;
import cn.com.ttblog.sssbootstrap_table.util.JWTUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 验证受到token保护的api
 */
@RestController
@RequestMapping("/api")
public class TokenApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenApiController.class);

	@RequestMapping(value = { "/time" },method=RequestMethod.GET)
	public Map time(Model model) {
		Map result=new HashedMap();
		result.put("success",true);
		Date time=new Date();
		result.put("time",time);
		LOGGER.info("invoke get time api,time:{}",time);
		return result;
	}

	@RequestMapping(value = { "/claims" },method=RequestMethod.GET)
	public Map claims(ModelMap model, HttpServletRequest request) {
		Map result=new HashedMap();
		result.put("success",true);
		result.put("claims",request.getAttribute("claims"));
		LOGGER.info("model:{},result:{}",model,result);
		return result;
	}

	/**
	 * 校验指定的token
	 * @param token
	 * @return
	 */
	@RequestMapping(value = { "/check/token" },method=RequestMethod.GET)
	public Map claims(@RequestParam("token") String token) {
		Map result=new HashedMap();
		result.put("success",true);
		try {
			result.put("data",JWTUtil.parseToken(token, ConfigConstant.JWT_SIGN_KEY));
		}catch (Exception e){
			result.put("success",false);
			result.put("data",e.getMessage());
		}
		return result;
	}
}
