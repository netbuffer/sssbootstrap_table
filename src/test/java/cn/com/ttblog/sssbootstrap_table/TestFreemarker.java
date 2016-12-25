package cn.com.ttblog.sssbootstrap_table;

import cn.com.ttblog.sssbootstrap_table.model.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.*;

public class TestFreemarker {

	private static final Logger LOG = LoggerFactory.getLogger(TestFreemarker.class);

	/**
	 * http://freemarker.org/ 文档
	 * @throws IOException
	 * @throws TemplateException
	 */
	@Test
	public void testTemplate() throws IOException, TemplateException {
		Configuration configuration=new Configuration(Configuration.VERSION_2_3_25);
		URL uri=this.getClass().getResource("/");
		LOG.info("uri.getPath():{}",uri.getPath());
		configuration.setDirectoryForTemplateLoading(new File(uri.getPath()));
		String ftp="index";
		Template template=configuration.getTemplate(ftp+".ftl");
		Map data=new HashMap<>();
		data.put("user","tt");
		Date d=new Date();
		LOG.info("current date:{}",d);
		data.put("d",d);
		int size=5;
		Set<User> users=new HashSet<>(size);
		for (int i = 0; i < size; i++) {
			User u = new User();
			u.setAge(i+1 + new Random().nextInt(1));
			u.setAdddate((int)(System.currentTimeMillis() / 1000));
			u.setName("batch-add-user:"+i);
			u.setDeliveryaddress("收货地址");
			u.setPhone("1324");
			u.setSex("男");
			users.add(u);
		}
		data.put("users",users);
		StringWriter sw=new StringWriter();
		template.process(data,sw);
		sw.flush();
		sw.close();
		LOG.info("sw.getBuffer()：{}",sw.getBuffer());
	}
}