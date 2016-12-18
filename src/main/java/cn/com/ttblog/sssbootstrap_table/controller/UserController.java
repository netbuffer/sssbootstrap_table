package cn.com.ttblog.sssbootstrap_table.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import cn.com.ttblog.sssbootstrap_table.model.User;
import cn.com.ttblog.sssbootstrap_table.model.query.QueryUser;
import cn.com.ttblog.sssbootstrap_table.service.IUserService;
import cn.com.ttblog.sssbootstrap_table.util.BeanMapUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private IUserService userService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

//	@InitBinder
//	public void initBinder(WebDataBinder binder){
//	}

	@RequestMapping(method=RequestMethod.GET)
	public String add(Map m){
		logger.debug("to get user page");
		m.put("from","user");
		return "user/add";
	}

	@ModelAttribute
	public void getUser(@RequestParam(value = "id",required = false) Long id,Map m){
		if(id!=null&&id>0){
			User user=userService.getUserById(id);
			logger.info("modelattribute执行!,查询用户:{},放入map:{}",id,user);
			m.put("user",user);
		}
	}
	//增
	@RequestMapping(method=RequestMethod.POST)
	public String save(@Valid User user){
		logger.debug("save user:{}",user);
		if(user.getAdddate()==null){
			user.setAdddate((int)(System.currentTimeMillis() / 1000));
		}
		userService.addUser(user);
		return "redirect:user";
	}

	//删
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	public Map delete(@RequestParam(value = "id",required =false)Long id){
		Map result=new HashMap(2);
		logger.debug("delete user:{}",id);
		try {
			result.put("success",true);
			userService.deleteById(id);
		}catch (Exception e){
			result.put("success",false);
			result.put("msg",e.getMessage());
			logger.error("delete user出错:{}",id,e);
		}
		return result;
	}

	//改
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public Map update(@Valid User user, BindingResult br){
		logger.debug("update user bindiing result:{}",br);
		if(br.getFieldErrorCount()>0){
			logger.error("User校验错误:{}",br.getFieldErrors());
		}
		Map result=new HashMap(2);
		logger.debug("update user:{}",user);
		try {
			result.put("success",true);
			userService.addUser(user);
		}catch (Exception e){
			result.put("success",false);
			result.put("msg",e.getMessage());
			logger.error("save user出错:{}",user,e);
		}
		result.put("user",user);
		return result;
	}

	@RequestMapping(value="/photos",method={RequestMethod.GET,RequestMethod.HEAD})
	public String photos() {
		logger.debug("go to user-photos");
		return "user/photos";
	}
	
	@RequestMapping("/showUser/{id}")
	public String toIndex(@PathVariable("id") int id,
			HttpServletRequest request, Model model) {
		logger.info("进入:{},参数:{}", request.getRequestURI(), model.toString());
		int userId = id;
		User user = userService.getUserById(userId);
		model.addAttribute("user", user);
		return "su";
	}

	@RequestMapping("/testmodel")
	public ModelAndView model() {
		ModelAndView mav = new ModelAndView();
		User u = new User();
		u.setName("tianyu");
		u.setAge(11);
		u.setDeliveryaddress("收货地址");
		mav.addObject("model", u);
		return mav;
	}
	
	/**
	 * 访问pdf视图 http://localhost:8080/sssbootstrap_table/user/userpdfview.pdf
	 * @param model
	 * @return
	 */
	@RequestMapping("/userpdfview")
	public String userpdfview(Model model) {
		model.addAttribute("users",userService.getUserList("desc",10,0));
		List<String> columns=new ArrayList<>();
		columns.add("姓名");
		columns.add("性别");
		columns.add("年龄");
		columns.add("手机号");
		model.addAttribute("columns", columns);
		return "userpdfview";
	}
	
	/**
	 * 访问excel视图 http://localhost:8080/sssbootstrap_table/user/userxlsview.xls
	 */
	@RequestMapping("/userxlsview")
	public String userxlsview(Model model) {
		List<User> users=userService.getUserList("desc",10,0);
		List<Map<String, Object>> mps = new ArrayList<Map<String, Object>>(
				users.size());
		int userCount=users.size();
		for (int i = 0; i < userCount; i++) {
			Map<String, Object> m = BeanMapUtil.transBean2Map(users.get(i));
			mps.add(m);
		}
		model.addAttribute("users",mps);
		List<String> columns=new ArrayList<>();
		columns.add("姓名");
		columns.add("性别");
		columns.add("年龄");
		columns.add("手机号");
		List<String> keys=new ArrayList<>();
		keys.add("name");
		keys.add("sex");
		keys.add("age");
		keys.add("phone");
		model.addAttribute("keys", keys);
		model.addAttribute("columns", columns);
		return "userxlsview";
	}

	/**
	 * http://localhost:8080/sssbootstrap_table/user/userlist?order=asc&limit=10
	 * &offset=0
	 * 
	 * @param order
	 * @param limit
	 * @param offset
	 * @param model
	 * @return
	 */
	@RequestMapping("/userlist")
	public String userlist(@RequestParam(value="search",required=false)String search,String order, int limit, int offset, Model model) {
		long startTime = System.nanoTime();
		logger.info("参数:{},{},{}", order, limit, offset);
		if(search!=null){
			try {
				//get参数乱码问题:http://luanxiyuan.iteye.com/blog/1849169
				search=new String(search.getBytes("ISO-8859-1"), "UTF-8");
				logger.info("查询参数:{}", search);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		}
		List<User> users =search==null? userService.getUserList(order, limit, offset): userService.getUserList(search,order, limit, offset);
		long total = userService.getUserListCount();
		Map<String, Object> params = new HashMap<String, Object>();
		model.addAttribute("total", total);
		model.addAttribute("rows", users);
		logger.info("结果:{}", params);
		long estimatedTime = System.nanoTime() - startTime;
		logger.debug("userlist execute with:{}ns",estimatedTime);
		return "userlist";
	}
	
	@RequestMapping("/userlistq")
	public String userlist(QueryUser query,ModelMap map) {
		logger.info("查询参数:{}", query);
		map.addAttribute("query", query);
		String querystr=JSON.toJSONString(query);
		map.addAttribute("querystr",querystr);
		logger.debug("querystr:{}",querystr);
		return "testquery";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(@RequestParam(value="id",required=false,defaultValue="0") String id) {
		logger.info("删除用户:{}", id);
		userService.deleteById(Long.parseLong(id));
		return "delete";
	}

	@RequestMapping("/showUserXML")
	public ModelAndView showUserXML(HttpServletRequest request, Model model) {
		ModelAndView mav = new ModelAndView("xStreamMarshallingView");
		int userId = Integer.parseInt("1");
		User user = userService.getUserById(userId);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/u/{id}", headers = "Accept=application/json")
	public @ResponseBody User getEmp(@PathVariable String id) {
		User e = userService.getUserById(1);
		return e;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/us", headers = "Accept=application/json")
	public @ResponseBody List<User> ListinggetAllEmp() {
		List<User> us = new ArrayList<User>();
		us.add(userService.getUserById(1));
		us.add(userService.getUserById(2));
		return us;
	}
}