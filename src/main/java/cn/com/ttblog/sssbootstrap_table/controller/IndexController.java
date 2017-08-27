package cn.com.ttblog.sssbootstrap_table.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONArray;

//import com.codahale.metrics.annotation.Timed;
import cn.com.ttblog.sssbootstrap_table.event.LoginEvent;
import cn.com.ttblog.sssbootstrap_table.model.User;
import cn.com.ttblog.sssbootstrap_table.service.IUserService;
import cn.com.ttblog.sssbootstrap_table.util.BeanMapUtil;
import cn.com.ttblog.sssbootstrap_table.util.POIExcelUtil;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * index
 */
@Controller(value="mainindex")
@RequestMapping("/")
public class IndexController {
	public IndexController(){
		
	}
	@Resource
	private IUserService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired  
    private ApplicationContext applicationContext;
	@Resource
	private RequestMappingHandlerMapping handlerMapping;

	/**
	 * 查看spring mvc所有映射的url
	 * @return
	 */
	@RequestMapping("/mappings")
	public String mappings(Model model){
		LOGGER.info("查看springmvc映射");
		model.addAttribute("handlerMappings",handlerMapping.getHandlerMethods());
		return "mappings";
	}

	@RequestMapping("/login")
	public String login(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, String username, String password,@RequestParam(value="requri",required=false) String requri) {
//		RequestContextUtils.getWebApplicationContext(request)
		LOGGER.info("进入username:{},pwd:{},requri:{}", username, password,requri);
		if (username.equals(ConfigConstant.VAL_USERNAME)
				&& password.equals(ConfigConstant.VAL_PWD)) {
			session.setAttribute(ConfigConstant.ISLOGIN, true);
			session.setAttribute(ConfigConstant.USERNAME, username);
			Cookie c = new Cookie(ConfigConstant.USERNAME, username);
			c.setMaxAge(86400);
			response.addCookie(c);
			Map<String, String> param=new HashMap<String,String>();
			param.put("loginname", username);
			param.put("logintime", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
			param.put("loginip", request.getRemoteAddr());
			applicationContext.publishEvent(new LoginEvent(param));  
			if(requri!=null&&requri.length()>0){
				String uri=new String(Base64.decodeBase64(requri));
				String touri=uri.substring(request.getContextPath().length()+1);
				LOGGER.debug("request.getContextPath():{}  decode-requri:{}  touri:{}",request.getContextPath(),uri,touri);
//				/sssbootstrap_table
//				/sssbootstrap_table/test/form?null
				return "redirect:/"+touri;
			}
			return "redirect:/manage.html";
		} else {
			return "redirect:/index.html?requri="+requri;
		}
	}
	
	@RequestMapping("/demo")
	public String demolist() {
		LOGGER.debug("demo");
		return "redirect:/demolist.html";
	}

	@RequestMapping("/exit")
	public String exit(HttpSession session,HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("用户{}退出系统",session.getAttribute(ConfigConstant.USERNAME));
		//删除cookie
		Cookie cookie = new Cookie(ConfigConstant.USERNAME, null); 
		cookie.setMaxAge(0);
		session.invalidate();
		LOGGER.debug("exit c2:{}",cookie);
		response.addCookie(cookie);
		return "redirect:/index.html";
	}
	
	@RequestMapping("/newdata")
	public String newdata(HttpSession session, Model model) {
		DecimalFormat df = new DecimalFormat("0.00");
		// Display the total amount of memory in the Java virtual machine.
		long totalMem = Runtime.getRuntime().totalMemory() / 1024 / 1024;
		System.out.println(df.format(totalMem) + " MB");
		// Display the maximum amount of memory that the Java virtual machine
		// will attempt to use.
		long maxMem = Runtime.getRuntime().maxMemory() / 1024 / 1024;
		System.out.println(df.format(maxMem) + " MB");
		// Display the amount of free memory in the Java Virtual Machine.
		long freeMem = Runtime.getRuntime().freeMemory() / 1024 / 1024;
		System.out.println(df.format(freeMem) + " MB");
		LOGGER.info("执行前:{}", model);
		int newcount = userService.getNewData();
		String username = session.getAttribute(ConfigConstant.USERNAME).toString();
		model.addAttribute("newcount", newcount);
		model.addAttribute("username", username);
		LOGGER.info("执行后:{}", model);
		return "newdata";
	}

	@RequestMapping("teststr")
	public @ResponseBody String teststr() {
		return "this is str";
	}
	
//	@Timed
	@RequestMapping("/datacount")
	public @ResponseBody Map<String, Object> datacount() {
		LOGGER.debug("获取datacount");
		List<Map<String, Object>> counts = userService.getDataSum();
		JSONArray categorys = new JSONArray();
		JSONArray nums = new JSONArray();
		for (Map<String, Object> m : counts) {
			categorys.add(m.get("adddate")==null?"":m.get("adddate").toString());
			nums.add(m.get("num").toString());
		}
		LOGGER.debug("categorys:{},nums:{}", categorys, nums);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("c", categorys);
		data.put("d", nums);
		return data;
	}

	@RequestMapping("/export")
	public ResponseEntity<byte[]> export(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,@RequestHeader(value = "USER-AGENT") String userAgent) throws UnsupportedEncodingException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		List<User> users = userService.getUserList("desc", 10, 0);
		String projectPath = request.getServletContext().getRealPath("export")
				+ File.separator;
		File dir=new File(projectPath);
		if(!dir.exists()){
			if(dir.mkdir()){
				LOGGER.debug("创建目录:{}",dir.getAbsolutePath());
			}else{
				LOGGER.debug("创建目录:{}失败!,请检查权限!",dir.getAbsolutePath());
				throw new RuntimeException("没有创建:"+dir.getAbsolutePath()+"目录的权限!");
			}
		}
		int userCount = users.size();
		List<Map<String, Object>> mps = new ArrayList<Map<String, Object>>(
				users.size());
		for (int i = 0; i < userCount; i++) {
			Map<String, Object> m = BeanMapUtil.transBean2Map(users.get(i));
			mps.add(m);
		}
		LOGGER.info("users:{}", mps);
		List<String> titles = new ArrayList<String>(mps.get(0).size() - 1);
		titles.add("adddate");
		titles.add("age");
		titles.add("deliveryaddress");
		titles.add("id");
		titles.add("name");
		titles.add("phone");
		titles.add("sex");
		List<String> columns = new ArrayList<String>(mps.get(0).size() - 1);
		columns.add("添加时间");
		columns.add("年龄");
		columns.add("收货地址");
		columns.add("ID");
		columns.add("名字");
		columns.add("手机");
		columns.add("性别");
		String file = projectPath + format.format(new Date()) + "."
				+ ConfigConstant.EXCELSTR;
		LOGGER.info("文件路径:{}", file);
		POIExcelUtil.export(titles,columns, mps, file);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String filename="下载file";
		if(userAgent.toLowerCase().indexOf("firefox") >= 0){
			try {
				filename = new String((filename+file.replace(projectPath, "")).getBytes("UTF-8"),"iso-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else {
			try {
				filename = URLEncoder.encode(filename+file.replace(projectPath, ""),"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		LOGGER.debug("下载文件名字:{}",filename);
		headers.setContentDispositionFormData("attachment",filename);
		try {
//			http://stackoverflow.com/questions/11203111/downloading-a-spring-mvc-generated-file-not-working-in-ie ie下载问题
			return new ResponseEntity<byte[]>(
					FileUtils.readFileToByteArray(new File(file)), headers,
					HttpStatus.OK);
//			HttpStatus.CREATED
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/files/{file_name}", method = RequestMethod.GET)
//	@ResponseBody
	public FileSystemResource getFile(@PathVariable("file_name") String fileName,HttpServletRequest request) {
	    return new FileSystemResource(new File(request.getServletContext().getRealPath("export")+ File.separator+fileName+".xls")); 
	}

	@RequestMapping("/testerror")
	public String testthrowException() {
		throw new RuntimeException("test error");
	}

	/**
	 * 单独设置某个session的超时时间
	 * @param session
	 * @param seconds
	 * @return
	 */
	@RequestMapping("/session/timeout")
	@ResponseBody
	public HttpSession sessionTimeout(HttpSession session, @RequestParam(value = "seconds",required = false,defaultValue = "60") Integer seconds) {
		//seconds
		session.setMaxInactiveInterval(seconds);
		return session;
	}

	@ExceptionHandler
	public ModelAndView handleAllException(Exception ex) {
		ModelAndView mav = new ModelAndView("500");
		mav.addObject("errMsg", ex.getMessage());
		return mav;
	}

	/**
	 * 数据转换处理
	 */
	// @InitBinder
	// public void bind(WebDataBinder binder){
	// //设置不转换name，自行处理
	// binder.setDisallowedFields("name");
	// }
}