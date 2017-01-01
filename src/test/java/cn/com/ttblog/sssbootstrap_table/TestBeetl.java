package cn.com.ttblog.sssbootstrap_table;

import cn.com.ttblog.sssbootstrap_table.model.User;
import org.beetl.core.BeetlKit;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * beetl使用:http://ibeetl.com/guide/#beetl
 * 基本按照js的语法来书写
 */
public class TestBeetl {

    private static final Logger LOG= LoggerFactory.getLogger(TestBeetl.class);

    /**
     * 字符串模板加载器
     * @throws IOException
     */
    @Test
    public void testStringTemplate() throws IOException {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("hello,${name}");
        t.binding("name", "beetl");
        LOG.debug("render:{}",t.render());
    }

    /**
     * 文件资源模板加载器
     * @throws IOException
     */
    @Test
    public void testFileResourceLoader() throws IOException {
        URL uri=this.getClass().getResource("/");
        FileResourceLoader resourceLoader = new FileResourceLoader(uri.getPath(),"utf-8");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("/index.btl");
        Map m=new HashMap();
        m.put("name","beetl");
        m.put("users",getUsers());
        Map mapdata=new HashMap();
        mapdata.put("name","hello");
        mapdata.put("age",10);
        m.put("mapdata",mapdata);
        t.binding(m);
        LOG.debug("render:{}",t.render());
    }

    public List<User> getUsers(){
        List<User> users=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User u = new User();
            u.setAge(i + new Random().nextInt(1));
            u.setAdddate((int)(System.currentTimeMillis() / 1000));
            u.setName("用户:"+i);
            u.setDeliveryaddress("收货地址");
            u.setPhone("1324");
            u.setSex("男");
            users.add(u);
        }
        return users;
    }

    @Test
    public void testBeetlKit(){
        Map m=new HashMap<>();
        m.put("name","world!");
        LOG.debug("BeetlKit.render(\"hello ${name}\",m):{}",BeetlKit.render("hello ${name}",m));
    }
}
