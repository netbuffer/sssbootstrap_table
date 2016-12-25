<!DOCTYPE html>
<html>
<head>
	<title>Hello World</title>
	<script src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<body>
	<h2>Hello :
	<#--流程判断-->
	<#if user=="world">
        <hr/>
        world
	<#else>
        not world，is：${user}
	</#if>
	</h2>
	<hr/>
	<#--遍历-->
	<#list users as user>
	name:${user.name}<br/>
	</#list>
    users[0]:${users[0]}
    <hr/>
	<#include "foot.ftl">
    <hr/>
	<#--引入commons下的方法-->
	<#include "commons.ftl" />
	<#--方法调用-->
	<@f_success></@f_success>
	<@f_success />
	<@f_fail errmsg="msg" />
	<@f_fail "错误信息" />
	<@f_nested>--------------</@f_nested>
	<hr/>
	<#--引入命名空间-->
	<#import "namespace.ftl" as np />
	<#assign email="netbuffer@163.com"/>
	<@np.f_success />
    current_email:${email}
	email:${np.email}<br/>
	<#--修改当前变量email-->
	<#assign email="n1@163.com"/>
	<#--修改np命名空间中的email变量-->
	<#assign email="j1@163.com" in np/>
    current_email:${email}
    email:${np.email}<br/>
	<#assign str="hello" />
	str(0..3):${str[0..3]}<br>
	<#--${abc} 直接引用未定义的变量会出错 -->
	<#--${abc!}输出空-->
	${abc!"没有abc"}
</body>
</html>