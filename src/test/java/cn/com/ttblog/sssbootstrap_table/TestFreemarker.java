package cn.com.ttblog.sssbootstrap_table;

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
import java.util.HashMap;
import java.util.Map;

public class TestFreemarker {

	private static final Logger LOG = LoggerFactory.getLogger(TestFreemarker.class);

	@Test
	public void testTemplate() throws IOException, TemplateException {
		Configuration configuration=new Configuration();
		URL uri=this.getClass().getResource("/");
		LOG.info("uri.getPath():{}",uri.getPath());
		configuration.setDirectoryForTemplateLoading(new File(uri.getPath()));
		String ftp="index";
		Template template=configuration.getTemplate(ftp+".ftl");
		Map data=new HashMap<>();
		data.put("user","tt");
		StringWriter sw=new StringWriter();
		template.process(data,sw);
		LOG.info("sw.getBuffer()：{}",sw.getBuffer());
	}
}