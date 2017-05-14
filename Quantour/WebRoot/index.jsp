<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">
<link href="//cdn.bootcss.com/bootstrap/3.3.1/css/bootstrap.css" rel="stylesheet">

<title>首页</title>
</head>
<body>
<h2><a href="toLogin.do">登录</a></h2>
</body>
</html>