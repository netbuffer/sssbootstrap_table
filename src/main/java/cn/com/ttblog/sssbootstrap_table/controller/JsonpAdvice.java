//package cn.com.ttblog.sssbootstrap_table.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJacksonValue;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;
//
///**
// * 测试spring 4.1之后增加的jsonp特性,适用于使用jackson处理，需要依赖jackson
// */
//@ControllerAdvice(basePackages = "cn.com.ttblog")
//public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(JsonpAdvice.class);
//    private static final String callback="callback";
//
//    public JsonpAdvice() {
//        super(callback);
//        LOGGER.info("启用spring mvc jsonp支持,jsonp 参数:{}",callback);
//    }
//
//    @Override
//    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
//        LOGGER.info("bodyContainer:{},contentType:{},returnType:{},request:{},response:{}",bodyContainer,contentType,returnType,request,response);
//        super.beforeBodyWriteInternal(bodyContainer, contentType, returnType, request, response);
//    }
//}