package cn.com.ttblog.sssbootstrap_table.beetl.function;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Echo implements Function{

    private static final Logger LOG= LoggerFactory.getLogger(Echo.class);

    @Override
    public Object call(Object[] objects, Context context) {
        LOG.debug("beetl function invoke! objects:{},context:{}",objects,context);
        Object o = objects[0];
        if (o != null){
            try{
                context.byteWriter.write(("<span style='color:green;font-weight:bold;'>"+o.toString()+"</span>").getBytes());
            }catch (IOException e){
                LOG.error("调用beetl函数出错:",e);
                throw new RuntimeException(e);
            }
        }
        return "";
    }
}
