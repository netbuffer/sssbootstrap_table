package cn.com.ttblog.sssbootstrap_table.filter;

import cn.com.ttblog.sssbootstrap_table.Constant.ConfigConstant;
import com.alibaba.fastjson.JSONObject;
import com.github.jscookie.javacookie.Cookies;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * https://github.com/nielsutrecht/jwt-angular-spring/blob/master/src/main/java/com/nibado/example/jwtangspr/JwtFilter.java
 */
@Component("jwtFilter")
public class JwtFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {
        boolean findToken = false;
        JSONObject jsonObject=new JSONObject();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String token = request.getHeader("Authorization");
        if (token != null && token.length() > 9) {
            findToken = true;
            token = token.substring(7); // The part after "Bearer "
        } else {
            Cookies cookies = Cookies.initFromServlet(request, response);
            token = cookies.get("token");
            if (token != null && token.length() > 3) {
                findToken = true;
            }
        }
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new ServletException("Missing or invalid Authorization header.");
//        }
        if (findToken) {
            try {
                final Claims claims = Jwts.parser().setSigningKey(ConfigConstant.JWT_SIGN_KEY)
                        .parseClaimsJws(token).getBody();
                request.setAttribute("claims", claims);
                LOGGER.info("签名通过:{}",claims);
            } catch (SignatureException e) {
                LOGGER.error("token签名错误:{}",e.getMessage());
                jsonObject.put("status",false);
                jsonObject.put("msg","invalid token");
                response.getWriter().write(jsonObject.toJSONString());
                return;
            }
            chain.doFilter(req, res);
        } else {
            LOGGER.info("未发现token内容");
            jsonObject.put("status",false);
            jsonObject.put("msg","missing token");
            response.getWriter().write(jsonObject.toJSONString());
            return;
        }
    }

}