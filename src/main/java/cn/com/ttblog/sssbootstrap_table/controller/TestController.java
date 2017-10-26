package cn.com.ttblog.sssbootstrap_table.controller;

import cn.com.ttblog.sssbootstrap_table.annotation.EnableLog;
import cn.com.ttblog.sssbootstrap_table.annotation.Token;
import cn.com.ttblog.sssbootstrap_table.model.Address;
import cn.com.ttblog.sssbootstrap_table.model.ExtendUser;
import cn.com.ttblog.sssbootstrap_table.model.User;
import cn.com.ttblog.sssbootstrap_table.util.AjaxUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Controller
@RequestMapping("/test")
@SessionAttributes("name")
public class TestController {
	private static final Logger LOG=LoggerFactory.getLogger(TestController.class);
	private Logger loggerAccess = LoggerFactory.getLogger("access");
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Logger logger = loggerAccess;
	@EnableLog
	private Logger INJECTLOG = null;

	@Autowired  
	private ApplicationContext applicationContext;
	
	@Resource
	private Properties configProperties;
	@Value("#{configProperties['url2']}")
	private String url;
	@Value("#{configProperties['mysql.connectTime']}")
	private Integer connectTime;
	@Autowired
	private CookieLocaleResolver cookieResolver;
	//锁
	private Lock lock=new ReentrantLock();
	//注入静态属性值
	private static String  JDBCURL;

	@Autowired
	private MessageSource ms;

	/**
	 * 当前控制器异常捕获,如果当前控制器未设置，则去@ControllerAdvice标记中的类寻找@ExceptionHandler标记的方法处理异常
	 * 需要使用ModelAndView来绑定模型数据
	 * @param e
	 * @return
	 */
	@ExceptionHandler({Exception.class})
	public ModelAndView handlerException(Exception e){
		ModelAndView modelAndView=new ModelAndView("error");
		LOG.error("控制器发生Exception级异常:{}",e.getMessage());
		modelAndView.addObject("errMsg",e.getMessage());
		return modelAndView;
	}

	@ExceptionHandler({RuntimeException.class})
	public ModelAndView handlerRunTimeException(RuntimeException e){
		ModelAndView modelAndView=new ModelAndView("error");
		LOG.error("控制器发生RunTime级异常:{}",e.getMessage());
		modelAndView.addObject("errMsg",e.getMessage());
		return modelAndView;
	}

	@RequestMapping(value = "injectlog",method = RequestMethod.GET)
	public String injectLog(){
		INJECTLOG.warn("INJECTLOG");
		return 	"test";
	}

	@RequestMapping(value = "divide",method = RequestMethod.GET)
	public String divide(){
		int i=1/0;
		return 	"test";
	}

	/**
	 * 获取国际化资源消息 i18n
	 * @return
	 */
	@RequestMapping(value = "msg",method = RequestMethod.GET)
	@ResponseBody
	public Map getMsg(){
		Map m=new HashMap(2);
		String us=ms.getMessage("Range.user.age",null,Locale.US);
		String zh_CN=ms.getMessage("Range.user.age",null,Locale.CHINA);
		m.put("us",us);
		m.put("zh_CN",zh_CN);
		LOG.info("get message locale info:{}",m);
		return m;
	}

	@RequestMapping(value = "msglocale",method = RequestMethod.GET)
	@ResponseBody
	public Map msglocale(Locale locale){
		Map m=new HashMap(2);
		String val=ms.getMessage("Range.user.age",null,locale);
		m.put("locale",locale);
		m.put("val",val);
		LOG.info("get message locale:{},info:{}",locale,m);
		return m;
	}

	//注入方法
	@Value("#{configProperties['jdbc.url']}")
    public void setJdbcUrl(String url) {
		JDBCURL = url;
    }
	
	//@RequestMapping不写value会默认映射类上的访问路径"/test"
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody String getTest() {
		LOG.debug("GET to /test");
		return "GET to /test";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody String postTest() {
		LOG.debug("POST to /test");
		return "POST to /test";
	}
	
	@RequestMapping(value = {"/getJdbcUrl" })
	public @ResponseBody String getJdbcUrl() {
		logger.debug("静态属性值:{}",JDBCURL);
		return JDBCURL;
	}
	
	@RequestMapping(value = {"/getbean/{name}" })
	public @ResponseBody Object getbean(@PathVariable("name") String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * 获取环境信息
	 * @return
	 */
	@RequestMapping(value = {"/env" })
	public @ResponseBody Environment getEnvironment() {
		return applicationContext.getEnvironment();
	}

	@RequestMapping(value = {"/{id}", "/index/{id}" })
	public String index(@PathVariable("id") int id, ModelMap m) {
		logger.debug("template id:{}", id);
		m.addAttribute("uri", id);
		m.addAttribute("showTime", System.currentTimeMillis() / 1000);
		m.addAttribute("test",id);
		return "index";
	}

	@RequestMapping(value = { "/getconfig" })
	public Object getConfig() {
		return JSONObject.toJSON(configProperties);
	}

	@RequestMapping(value = { "/geturl" })
	public @ResponseBody String getUrl() {
		logger.debug("url:{}", url);
		return url;
	}

	@RequestMapping(value = { "/getConnectTime" })
	public @ResponseBody Integer getConnectTime() {
		logger.debug("connectTime:{}", connectTime);
		return connectTime;
	}

	/**
	 * 直接写响应，会使用FastJsonHttpMessageConverter作json转换 访问路径会直接以json输出
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/getobj" })
	public @ResponseBody Map<String, Object> getobj() {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("key1", "v1");
		res.put("key2", "v2");
		return res;
	}

	@RequestMapping(value = { "/setSessionAttr" })
	public @ResponseBody String setSessionAttr(ModelMap m) {
		logger.debug("setSessionAttr");
		// 会被放到session中
		m.put("name", "this is name's value");
		return "success";
	}

	/**
	 * 返回本地化信息
	 * 
	 * @param locale
	 * @return
	 * @author genie
	 * @date 2016年6月13日 下午1:54:11
	 */
	@RequestMapping(value = { "/locale" })
	public @ResponseBody String locale(Locale locale) {
		logger.debug("locale:{}", locale);
		return locale.toString();
	}

	@RequestMapping("/lang")
	@ResponseBody
	public String lang(@RequestParam(defaultValue="zh",required=false,value="lang")String langType, HttpServletRequest request, HttpServletResponse response) {
		if (langType.equals("zh")) {
			Locale locale = new Locale("zh", "CN");
			// request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
			// locale);
			cookieResolver.setLocale(request, response, locale);
		} else if (langType.equals("en")) {
			Locale locale = new Locale("en", "US");
			// request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
			// locale);
			cookieResolver.setLocale(request, response, locale);
		} else
			cookieResolver.setLocale(request, response, LocaleContextHolder.getLocale());
		// request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
		// LocaleContextHolder.getLocale());
		return langType;
	}
	
	@Token(save=true,tokenname="testformtoken")
	@RequestMapping(value="/form",method=RequestMethod.GET)
	public String getform(){
		logger.debug("test get form ");
		return "user/add";
	}
	
	@Token(remove=true,tokenname="testformtoken",failuri="/user/error.jsp")
	@RequestMapping(value="/form",method=RequestMethod.POST)
	public String postform(@Valid User u){
//		,BindingResult result
		logger.debug("test post form:{}",u);
//		if(result.hasErrors()){
//			logger.info("校验user出错:"+ToStringBuilder.reflectionToString(result));
//			throw new RuntimeException("请填写正确的用户信息");
//		}
//		return "redirect:/register-success.html";
		//forward请求导致表单重复提交问题
		return "user/success";
	}
	
	/**
	 * 直接返回json数据 ,produces={"application/json"}
	 */
	@RequestMapping(value={"/uri"},method=RequestMethod.GET,headers={"Accept=application/json"})
	public JSONObject uri(HttpServletRequest request){
		JSONObject j=new JSONObject();
		j.put("request.getRequestURI", request.getRequestURI());
		j.put("request.getRequestURI().split(\"/\")", Arrays.deepToString(request.getRequestURI().split("/")));
		j.put("request.getRequestURL", request.getRequestURL());
		j.put("request.getServletContext().getContextPath", request.getServletContext().getContextPath());
		j.put("request.getServletContext().getRealPath(\"/\")", request.getServletContext().getRealPath("/"));
		return j;
	}
	
	/**
	 * 重定向拼接参数跳转
	 * @return
	 */
	@RequestMapping(value={"/redirect"})
	public String redirect(ModelMap m){
		//spring自动做了参数拼接
		logger.debug("redirect");
		m.put("param", "this is parameter");
		return "redirect:/test/1";
	}
	
	@RequestMapping(value={"/redirect2"})
	public String redirect2(RedirectAttributes attributes){
		logger.debug("redirect2");
		attributes.addAttribute("param", "this is parameter");
		return "redirect:/test/1";
	}

	/**
	 * flash attribute使用:原理就是将模型中的数据复制到会话session中，重定向后再从会话中取出对象放到请求模型中，并且移除会话中数据
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value={"/redirectflashattr"})
	public String redirectFlashAttr(RedirectAttributes redirectAttributes){
		logger.debug("redirect2");
		User user=new User();
		user.setName("user");
		user.setAge(22);
		user.setPhone("13023423423");
		redirectAttributes.addFlashAttribute("user",user);
		return "redirect:/test/receiveflashattr";
	}

	/**
	 * 取模型中的FlashAttribute
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/receiveflashattr"})
	@ResponseBody
	public User redirectFlashAttr(ModelMap model){
		logger.debug("after redirect get model:{}",model);
		return (User) model.get("user");
	}
	
	@RequestMapping(value={"/error"})
	public String error(ModelMap m){
		logger.debug("test error");
//		int i=1/0;
		try{
			throw new RuntimeException("test");
		}catch(Exception ex){
			m.put("ex",ExceptionUtils.getStackTrace(ex));
		}
		
		return "error";
	}
	
	@ResponseStatus(reason="test",value=HttpStatus.NO_CONTENT)
	@RequestMapping(value={"/status"})
	public String status(){
		logger.debug("status");
		return "error";
	}
	
	@RequestMapping(value={"/ajax"})
	public String ajax(HttpServletRequest request){
		logger.debug("request.getHeader(\"X-Requested-With\"):{}",request.getHeader("X-Requested-With"));
		logger.debug("is ajax:{}",AjaxUtils.isAjaxRequest(request));
		return "index";
	}
	
	@RequestMapping(value={"/access"})
	public String access(){
		loggerAccess.info("access");
		return "index";
	}
	
	@RequestMapping(value={"/syn"})
	public synchronized String testSynchronized(ModelMap model){
		logger.warn("{}-执行synchronized操作!", Thread.currentThread().getName());
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException("服务器错误");
		}
		if(model.get("syn")==null){
			model.put("syn", Thread.currentThread().getName());
		}
		logger.warn("{}-执行synchronized操作完成", Thread.currentThread().getName());
		return "index";
	}

	@RequestMapping(value={"/syn2"})
	public String testSynchronized2(ModelMap model){
		lock.lock();
		logger.warn("{}-执行synchronized2操作!", Thread.currentThread().getName());
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException("服务器错误2");
		}
		if(model.get("syn")==null){
			model.put("syn", Thread.currentThread().getName());
		}
		logger.warn("{}-执行synchronized操作完成2", Thread.currentThread().getName());
		lock.unlock();
		return "index";
	}
	
	@RequestMapping(value={"/ue"})
	public String ue(ModelMap model){
		logger.debug("open ueditor");
		return "ueditor/index";
	}
	
	@RequestMapping(value={"/{no}/uri"})
	public String uri(ModelMap model){
		return "redirect:/{no}/index";//springmvc会对模板变量中的值解析处理
//		return "{no}/index";
	}
	
	@RequestMapping(value={"/websocket"})
	public String websocket(ModelMap model){
		return "websocket";
	}
	
	/**
	 * 创建二维码
	 * @param response
	 * @param param
	 * @throws IOException
	 * @throws WriterException
	 */
	@RequestMapping(value={"/qr"},method=RequestMethod.GET)
	public void qr(HttpServletResponse response,@RequestParam(value="param",defaultValue="test",required=false) String param,
			@RequestParam(value="width",defaultValue="200") int width,@RequestParam(value="height",defaultValue="200") int height,
			@RequestParam(value="format",defaultValue="jpg") String format,@RequestParam(value="r",defaultValue="0") int r,
			@RequestParam(value="g",defaultValue="0") int g,@RequestParam(value="b",defaultValue="0") int b) 
			throws IOException, WriterException{
		logger.debug("使用zxing生成二维码,内容:{}",param);
//		int width = 200; // 图像宽度  
//        int height = 200; // 图像高度  
//        String format = "png";// 图像类型  
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
//        http://www.tuicool.com/articles/vQFZNfq
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); //编码
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); //容错率
        hints.put(EncodeHintType.MARGIN, 0);  //二维码边框宽度，这里文档说设置0-4,发现当宽高都大于100的时候，才会有无边框效果
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        BitMatrix bitMatrix = new MultiFormatWriter().encode(param,  
                BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵  
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0L);
		response.setContentType("image/jpeg");
		//颜色
		MatrixToImageConfig config = new MatrixToImageConfig(new Color(r,g,b).getRGB(),0xFFFFFFFF);
		ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix,config), "jpeg", jpegOutputStream);
		byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		ServletOutputStream respOs = response.getOutputStream();
		respOs.write(captchaChallengeAsJpeg);
		respOs.flush();
		respOs.close();
	}
	
	@RequestMapping(value={"/decodeqr"},method=RequestMethod.GET)
	public String decodeqr(){
		logger.debug("二维码解析");
		return "decodeqr";
	}


	@RequestMapping(value={"/i18n"},method=RequestMethod.GET)
	public String i18n(){
		return "test";
	}
	
	@RequestMapping(value={"/decodeqr"},method=RequestMethod.POST)
	@ResponseBody
	public String decodeqr(HttpServletRequest request,
			@RequestParam(value="qrimg",required=true)CommonsMultipartFile file,
			@RequestParam(value="name",required=false)String name
			)throws NotFoundException, IOException{
		logger.debug("二维码解析，附加数据:{}",name);
		if(file.isEmpty()){
			throw new RuntimeException("请上传文件");
		}
		BufferedImage image = ImageIO.read(file.getInputStream());  
        LuminanceSource source = new BufferedImageLuminanceSource(image);  
        Binarizer binarizer = new HybridBinarizer(source);  
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);  
        Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();  
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        //对图像进行解码 
        Result result = null;
        String resultTxt="incorrect qrimage";
        try {
			result=new MultiFormatReader().decode(binaryBitmap, hints);
		} catch (Exception e) {
			e.printStackTrace();
		}
        if(result!=null){
        	resultTxt=result.getText();
        }
        logger.debug("zxing解析二维码结果:{}",resultTxt);
        return resultTxt;
	}
	
	@RequestMapping(value={"/server"},method=RequestMethod.GET)
	@ResponseBody
	public String server(HttpServletRequest request){
		return request.getServerName();
	}
	
	@RequestMapping(value={"/bean"},method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> bean(User u,Address a){
		//访问http://localhost:8080/sssbootstrap_table/test/bean?name=mingzi&province=aaa&userId=1&sex=n，会自动组装对应的bean字段值
		Map<String, Object> result=new HashMap<>(2);
		result.put("u", u);
		result.put("a", a);
		logger.debug("test receive bean1:{}",result);
		return result;
	}
	
	@RequestMapping(value={"/beans"},method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> beansame(User u,ExtendUser eu){
		//http://localhost:8080/sssbootstrap_table/test/beans?name=mingzisex=n ,会自动组装到所有的bean中
		Map<String, Object> result=new HashMap<>(2);
		result.put("u", u);
		result.put("eu", eu);
		logger.debug("test receive bean2:{}",result);
		return result;
	}
	
	@RequestMapping(value={"/getids"},method=RequestMethod.GET)
	@ResponseBody
	public String getids(String ids){
		logger.debug("用户输入ids格式:{}",ids);
		return ids;
	}
	
	@RequestMapping(value={"/ua"},method=RequestMethod.GET)
	@ResponseBody
	public UserAgent ua(HttpServletRequest request){
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		logger.debug("用户ua:{}",userAgent);
		return userAgent;
	}
	
	/**
	 * 会在视图中添加key为“user”的model，而不是“u”！
	 * @param u
	 * @return
	 */
	@RequestMapping(value="/testmodelattr1")
	public String testmodelattr1(User u){
		logger.debug("testmodelattr1,u:{}",u);
		return "index";
	}
	
	/**
	 * 会在视图中添加key为“user”的model，和key为“u”的model！
	 * @param u
	 * @param m
	 */
	@RequestMapping(value="/testmodelattr2")
	public String testmodelattr2(User u,Model m){
		logger.debug("testmodelattr2,u:{}",u);
		m.addAttribute("u", u);
		return "index";
	}
	
	/**
	 * 会在视图中添加key为“u”的model，此时不会再添加key为“user”的model了
	 * @param u
	 * @return
	 */
	@RequestMapping(value="/testmodelattr3")
	public String testmodelattr3(@ModelAttribute(value="u")User u){
		logger.debug("testmodelattr3,u:{}",u);
		return "index";
	}
	
	@RequestMapping(value="/serverip")
	public @ResponseBody String getLocalIP(){
		String ip=AjaxUtils.getLocalIP();
		logger.debug("getLocalIP:{}",ip);
		return ip;
	}
	
	@RequestMapping(value="/indexurl")
	public @ResponseBody String indexurl(HttpServletRequest request){
		String indexurl=request.getScheme()+"://"+AjaxUtils.getLocalIP()+":"+configProperties.getProperty("appport")+request.getContextPath();
		logger.debug("indexurl:{}",indexurl);
		return indexurl;
	}

	/**
	 * springmvc 精确匹配路径
	 * ,headers = {"Accept-Language:zh-CN"}
	 * @return
	 */
	@RequestMapping(value="/ph",params = {"username","pwd=1"})
	public @ResponseBody String ph(){
		LOG.debug("execute");
		return "success";
	}

	/**
	 * 绑定header参数
	 * @param accept
	 * @return
	 */
	@RequestMapping(value="/headerval")
	public @ResponseBody String headerval(@RequestHeader(value = "Accept",required = false) String accept){
		LOG.debug("accept header val:{}",accept);
		return "accept:"+accept;
	}

	/**
	 * 绑定cookie参数
	 * @param sessionId
	 * @return
	 */
	@RequestMapping(value="/cookieval")
	public @ResponseBody String cookieval(@CookieValue(value = "JSESSIONID",required = false) String sessionId){
		LOG.debug("cookie JSESSIONID val:{}",sessionId);
		return "cookie JSESSIONID:"+sessionId;
	}

	@RequestMapping(value="/customview")
	public String customview(Map m){
		LOG.debug("customview");
		m.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		return "custom_view";//需要返回视图bean的id
//		return "customView";
	}

	/**
	 * 需要在mvc:annotation-driven中启用该功能enable-matrix-variables="true"
	 * @MatrixVariable使用测试
	 * 匹配 url :/pets/42;q=11;r=22
	 * @param petId
	 * @param q
	 */
	@RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET)
	@ResponseBody
	public Map findPet(@PathVariable String petId,@MatrixVariable(required = false) int q,Map model){
		LOG.debug("q:{}",q);
		model.put("q",q);
		return model;
	}
	//匹配:/owners/42;q=11/pets/21;q=22
	@RequestMapping(value = "/owners/{ownerId}/pets/{petId}", method = RequestMethod.GET)
	@ResponseBody
	public Model findPet(@MatrixVariable(value = "q",pathVar="ownerId") int q1,
						 @MatrixVariable(value = "q",pathVar="petId") int q2,Model model){
		LOG.debug("q1:{},q2:{}",q1,q2);
		model.addAttribute("q1",q1);
		model.addAttribute("q2",q2);
		return model;
	}
	//匹配/ownerss/42;q=11;r=12/pets/21;q=22;s=23
	@RequestMapping(value = "/ownerss/{ownerId}/pets/{petId}", method = RequestMethod.GET)
	@ResponseBody
	public Model findPet(@MatrixVariable Map<String,String> matrixVars,
						 @MatrixVariable(pathVar = "petId") Map<String,String> petMatrixVars,Model model){
		model.addAttribute("matrixVars",matrixVars);
		model.addAttribute("petMatrixVars",petMatrixVars);
		LOG.debug("model:{}",model);
		return model;
	}

	/**
	 * spring form标签使用
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/spring-form")
	public String findPet(User user,Model model){
		LOG.debug("user:{},model:{}",user,model);
		Map<Integer, String> ballMap = new HashMap<Integer, String>();
		ballMap.put(1, "篮球");
		ballMap.put(2, "足球");
		ballMap.put(3, "乒乓球");
		ballMap.put(4, "羽毛球");
		ballMap.put(5, "排球");
		model.addAttribute("ballMap",ballMap);
		return "spring-form";
	}

	@RequestMapping(value = "/path/{path}")
	public String findPet(@PathVariable("path") String path){
		LOG.info("path路径变量:{}",path);
		//url路径变量会自动添加到request作用域中
		return "path";
	}

	/**
	 * 测试接收map参数
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/receive/mapparam")
	@ResponseBody
	public Map receiveMap(@RequestParam Map map){
		LOG.debug("receive map param:{}",map);
		return map;
	}

	@RequestMapping(value = "/receive/mapparam2")
	@ResponseBody
	public Map receiveMap(@RequestParam Map map,@RequestParam(value = "a",required = false) String a){
		LOG.debug("receive map param:{},a:{}",map,a);
		return map;
	}

	/**
	 *
	 * @param request
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/session")
	@ResponseBody
	public User sessionVal(HttpServletRequest request,@RequestParam(value = "type",required = false,defaultValue = "c") String type){
		HttpSession session=request.getSession();
		switch (type){
			case "c":
				User user=new User();
				user.setName("test");
				user.setAge(20);
				session.setAttribute("user",user);
				break;
			case "u":
				//获取的是对象的引用，修改对象属性，session中数据也就变了
				User u= (User) session.getAttribute("user");
				u.setName(u.getName()+"-update");
				logger.info("u.getClass().getName():{},u:{},u.gethash:{}",u.getClass().getName(),u,u.getClass().hashCode());
				break;
			default:
				logger.info("type:{}",type);
		}
		return (User) session.getAttribute("user");
	}

	/**
	 * return null view ,404页面
	 * @return
	 */
	@RequestMapping(value = "nullview")
	public String nullView(){
		LOG.info("返回空视图");
		return null;
	}
}
