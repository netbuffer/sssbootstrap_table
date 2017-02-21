<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx">
    ${pageContext.request.scheme}://${pageContext.request.serverName}<c:if test="${pageContext.request.serverPort!=80 }">:${pageContext.request.serverPort}</c:if>${pageContext.request.contextPath }
</c:set>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>spring-form测试</title>
    <script src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
        });
    </script>
</head>
<body>
    <h1>spring-form绑定</h1><a href="http://elim.iteye.com/blog/1807330" target="_blank">http://elim.iteye.com/blog/1807330</a>
    <form:form action="${ctx}/test/spring-form" method="post" commandName="user">
        name:<form:input path="name"  id="name"/><br/>
        age:<form:input path="age"  id="age"/><br/>
        性别: <form:radiobutton path="sex" value="1"/>男&emsp;<form:radiobutton path="sex" value="0"/>女<br/>
        <form:select path="deliveryaddress" items="${ballMap}"/>
        <form:password path="id" />
        <form:hidden path="phone" />
        所有错误信息:<form:errors path="*"/>
        Name的错误信息:<form:errors path="name"/>
        <form:button name="submit" value="submit">submit</form:button>
    </form:form>
</body>
</html>