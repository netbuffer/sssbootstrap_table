package cn.com.ttblog.sssbootstrap_table;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import java.io.StringWriter;

/**
 * thymeleaf 非web环境渲染
 */
public class TestThymeleaf {

    private static final Logger LOG= LoggerFactory.getLogger(TestThymeleaf.class);

    @Test
    public void test(){
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("HTML");
        templateEngine.setTemplateResolver(templateResolver);
        Context context = new Context();
        context.setVariable("name", "World");
        StringWriter stringWriter = new StringWriter();
        templateEngine.process("hello.html", context, stringWriter);
        LOG.debug("html:{}",stringWriter);
    }
}
