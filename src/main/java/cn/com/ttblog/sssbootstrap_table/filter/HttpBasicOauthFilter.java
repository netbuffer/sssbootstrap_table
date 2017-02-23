package cn.com.ttblog.sssbootstrap_table.filter;

import cn.com.ttblog.sssbootstrap_table.controller.ConfigConstant;
import cn.com.ttblog.sssbootstrap_table.util.AntPathMatcherUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * HTTP基本认证 basic认证,实际使用需要使用https环境
 */
public class HttpBasicOauthFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(HttpBasicOauthFilter.class);

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authPaths = filterConfig.getInitParameter("authPath");
        boolean enable=Boolean.parseBoolean(filterConfig.getInitParameter("enable"));
        //不起用的情况下直接通过
        if(!enable){
            filterChain.doFilter(request,response);
            return ;
        }
        if(StringUtils.isBlank(authPaths)){
            filterChain.doFilter(request, response);
        }else {
            String[] authPath = authPaths.split("\n");
            int length=authPath.length;
            for(int i=0;i<length;i++){
                authPath[i]=authPath[i].trim();
            }
            LOG.debug("需要http basic 路径:{},length:{},requri:{}", Arrays.toString(authPath),authPath.length,request.getRequestURI());
            if(AntPathMatcherUtil.isMatch(authPath,request.getRequestURI())){
                String sessionAuth = (String) request.getSession().getAttribute("auth");
                if (sessionAuth != null) {
                    LOG.info("已经经过basic认证");
                    filterChain.doFilter(request, response);
                } else {
                    if (!checkHeaderAuth(request, response)) {
                        response.setStatus(401);
                        response.setHeader("Cache-Control", "no-store");
                        response.setDateHeader("Expires", 0);
                        response.setHeader("WWW-authenticate", "Basic Realm=\"require auth\"");
                    }else {
                        filterChain.doFilter(request, response);
                    }
                }
            }else {
                filterChain.doFilter(request,response);
            }
        }
    }

    private boolean checkHeaderAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String auth = request.getHeader("Authorization");
        LOG.info("auth encoded in base64 is " + auth);
        if ((auth != null) && (auth.length() > 6)) {
            auth = auth.substring(6, auth.length());
            String decodedAuth = new String(Base64.decodeBase64(auth));
            LOG.info("auth decoded from base64 is " + decodedAuth);
            try {
                if(decodedAuth.split(":").length!=2){
                    return false;
                }
            }catch (Exception e){
                LOG.error("提取认证信息出错:",e);
                return false;
            }
            String[] authinfo=decodedAuth.split(":");
            String userName=authinfo[0];
            String password=authinfo[1];
            if (userName.equals(ConfigConstant.VAL_USERNAME)
                    && password.equals(ConfigConstant.VAL_PWD)) {
                request.getSession().setAttribute("auth", decodedAuth);
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public void destroy() {
    }
}
