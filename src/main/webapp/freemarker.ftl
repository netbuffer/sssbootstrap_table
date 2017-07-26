<!DOCTYPE html>
<html>
<head>
	<title>freemarker template</title>
	<script src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript">
	</script>
</head>
<body>
	<#--直接使用日期类型会报错-->
	<#--${currentDate}-->
	时间格式渲染:,格式化后:${currentDate?string("M月dd日 HH:mm")}<br/>
	users:<br/><#list users as user>
		打印对象:${user}<br/>
		姓名:${user.name}<br/>
	</#list>
	<br />
    mapData:a:${mapData.a},b:${mapData.b}<br/>
	遍历mapData-key/value:<br/><#list mapData?keys as key>
		key:${key},value:${mapData["${key}"]}<br/>
	</#list>
	遍历mapData-value:<#list mapData?values as value>
    ${value}<br/>
	</#list>
	<br/><br/>
	listData<hr/>
	<#list listData as d>
    	list:${d}<br/>
	</#list><hr/>
	<#list ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"] as x>
	${x}&emsp;
	</#list>
	<#list 1..5 as i>
	${i}&emsp;
	</#list>
	<#list 5..1 as i>
	${i}&emsp;
	</#list>
	<#list [1 + 1, "abc"] as o>
	${o}&emsp;
	</#list>
	<h1>字符串渲染</h1>
	${"我的文件保存在C:\\盘"}<br/>
	${'我名字是\"annlee\"'}<br/>
    可以在指定字符串内容的引号前增加r标记,在r标记后的内容将会直接输出:${r"${foo}"}&emsp;${r"C:\foo\bar"}<br/>
	<h1>判断对象为空</h1>
	${abc!'name为空'}
	<#if abc??>
		${abc}
	<#else>
	    变量abc为空
	</#if>
</body>
</html>
