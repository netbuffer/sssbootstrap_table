package cn.com.ttblog.sssbootstrap_table.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;
import cn.com.ttblog.sssbootstrap_table.model.User;
import cn.com.ttblog.sssbootstrap_table.service.IUserService;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Restful Controller, 直接输出内容，不调用template引擎
 * @package cn.com.ttblog.sssbootstrap_table.controller
 * @author netbuffer
 */
@RestController
@RequestMapping("/restapi")
public class ApiController {
	private static final String SUFFIX =".ftl" ;
	static Configuration configuration=null;
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	static {
		configuration=new Configuration(Configuration.VERSION_2_3_25);
		URL uri=ApiController.class.getResource("/");
		try {
			logger.info("加载freemarker引擎");
			configuration.setDirectoryForTemplateLoading(new File(uri.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Autowired
	private IUserService userService;

	@RequestMapping(value = { "", "/{id}", "/index/{id}" },method=RequestMethod.GET)
	public User index(@PathVariable("id") int id) {
		User u=userService.getUserById(id);
		logger.debug("restapi get user id:{},query user:{}",id,u);
		return u;
	}

	/**
	 * 返回指定ftl渲染后的html
	 * 访问http://localhost:8080/sss/test/1 测试
	 * @param view
	 * @return
	 */
	@RequestMapping(value = {  "/html" },method=RequestMethod.GET)
	public String index(@RequestParam(value = "view",required = false) String view) {
		Template template=null;
		try {
			template=configuration.getTemplate(view+SUFFIX,"utf-8");
		} catch (IOException e) {
			logger.error("获取模板:{}出错,",view,e);
		}
		try {
			return FreeMarkerTemplateUtils.processTemplateIntoString(template,null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return "";
	}

}
