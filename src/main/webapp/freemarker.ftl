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
	</#list>
</body>
</html>
