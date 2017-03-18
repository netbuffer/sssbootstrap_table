<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>mappings</title>
<link rel="icon" href="favicon.ico">
<link href="http://cdn.bootcss.com/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div class="container-fluid">
		<div class="page-header">
			<h1>springmvc映射路径<small>Subtext for header</small></h1>
		</div>
		<table class="table table-bordered table-hover table-condensed">
			<th>映射路径</th><th>类</th>
			<c:forEach items="${handlerMappings}" var="mapping">
				<tr>
					<td>${mapping.key}</td>
					<td>${mapping.value.beanType}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>