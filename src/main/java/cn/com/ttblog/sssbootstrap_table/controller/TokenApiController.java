package cn.com.ttblog.sssbootstrap_table.controller;

import cn.com.ttblog.sssbootstrap_table.constant.ConfigConstant;
import cn.com.ttblog.sssbootstrap_table.util.JWTUtil;
import com.google.common.collect.Maps;
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
	 * 创建token
	 * http://localhost:8082/sss/api/create/token?sub=title&issuer=https://www.baidu.com&claims1=aaa
	 * @param sub
	 * @param issuer
	 * @param claims1
	 * @param claims2
	 * @return
	 */
	@RequestMapping(value = { "/create/token" },method=RequestMethod.GET)
	public String createToken(@RequestParam(value = "sub",required = false) String sub,
							  @RequestParam(value = "issuer",required = false) String issuer,
							  @RequestParam(value = "claims1",required = false) String claims1,
							  @RequestParam(value = "claims2",required = false) String claims2) {
		//jwt中的数据不能放敏感数据,生成的jwt只是被base64了，可以转码看到明文
		Map tokenParam= Maps.newHashMap();
		tokenParam.put("sub",sub);
		tokenParam.put("issuer",issuer);
		Map claims=Maps.newHashMap();
		claims.put("claims1",claims1);
		claims.put("claims2",claims2);
		tokenParam.put("claims",claims);
		return JWTUtil.createToken(tokenParam, ConfigConstant.JWT_SIGN_KEY);
	}

	/**
	 * 校验指定的token
	 * http://localhost:8082/sss/api/check/token?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aXRsZSIsImNsYWltczEiOiJhYWEiLCJjbGFpbXMyIjpudWxsLCJpc3MiOiJodHRwczovL3d3dy5iYWlkdS5jb20ifQ.DyMIUhPGvP2CNgZ0VkKs3GtLT3gsH9ekPQ70Ip0TAIOKBdbyRnngOUkZckpy12D5-McMFLpNJgADqax-mVo6Sw
	 * @param token
	 * @return
	 */
	@RequestMapping(value = { "/check/token" },method=RequestMethod.GET)
	public Map claims(@RequestParam("token") String token,@RequestParam(value = "check",required = false) Integer check) {
		Map result=new HashedMap();
		result.put("success",true);
		try {
			if(check==null||check.equals(1)){
				result.put("data",JWTUtil.parseToken(token, ConfigConstant.JWT_SIGN_KEY));
			}else {
				result.put("data",JWTUtil.parseToken(token));
			}
		}catch (Exception e){
			result.put("success",false);
			result.put("data",e.getMessage());
		}
		return result;
	}
}
