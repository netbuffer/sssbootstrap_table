package cn.com.ttblog.sssbootstrap_table.constant;

public class ConfigConstant {
	/**
	 * user表字符串
	 */
	public static final String USERTABLE="user";
	public static final String ISLOGIN = "islogin";
	public static final String USERNAME = "username";
	public static final String EXCELSTR = "xls";
	public static final String PROJECTNAME = "sssbootstrap_table";
	public static final String VAL_USERNAME = "admin";
	public static final String VAL_PWD = "admin";
	/**
	 * jwt的secret key,用来生成签名部分,校验签名是否合法，判断数据包是否被篡改
	 * 这个key是base64形式的,其它视作不合法字符
	 * https://baike.baidu.com/item/base64/8545775?fr=aladdin
	 */
	public static final String JWT_SIGN_KEY = "sssbootstrap_table";
}
