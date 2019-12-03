<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2019/11/21
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>图片上传页面</title>
</head>
<body>
<form action="/api/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="filename">
    <input type="submit" name="上传">
</form>
<p>${ msg}</p>
<p>${url}</p>
</body>
</html>
