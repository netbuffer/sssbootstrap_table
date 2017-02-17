package cn.com.ttblog.sssbootstrap_table;

import cn.com.ttblog.sssbootstrap_table.model.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFastJson {

    private static final Logger LOG= LoggerFactory.getLogger(TestFastJson.class);

    @Test
    public void testa(){
        User user=new User();
        user.setName("test");
        String  jsonString = JSON.toJSONString(user, SerializerFeature.BrowserCompatible);
        LOG.debug("user json str:{}",jsonString);
    }
}
