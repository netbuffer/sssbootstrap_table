package cn.com.ttblog.sssbootstrap_table.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("custom_view")
public class CustomView implements View{
    private static Logger LOG= LoggerFactory.getLogger(CustomView.class);

    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ObjectMapper om=new ObjectMapper();
        String json=om.writeValueAsString(model);
        LOG.info("转换模型数据:{}-{}",model,json);
        response.getWriter().write(json);
    }
}
