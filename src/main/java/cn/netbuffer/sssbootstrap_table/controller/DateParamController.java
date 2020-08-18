package cn.netbuffer.sssbootstrap_table.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;

@Controller
@RequestMapping("/date")
public class DateParamController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DateParamController.class);

//	@InitBinder
//	public void initBinder(WebDataBinder binder){
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
//	}

	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public Date add(Date date){
		LOGGER.info("接收到date类型参数:{}",date);
		return date;
	}

}